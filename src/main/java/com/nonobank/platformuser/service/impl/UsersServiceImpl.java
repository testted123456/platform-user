package com.nonobank.platformuser.service.impl;

import com.nonobank.platformuser.component.LdapComponent;
import com.nonobank.platformuser.component.MyUserException;
import com.nonobank.platformuser.conf.ResponseCode;
import com.nonobank.platformuser.dto.MongoRepository;
import com.nonobank.platformuser.entity.ldapEntity.LdapUserEntity;
import com.nonobank.platformuser.entity.mongoEntity.*;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.service.UsersService;
import com.nonobank.platformuser.utils.CharsUtil;
import com.nonobank.platformuser.utils.ResponseUtil;
import com.nonobank.platformuser.utils.SecretUtil;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by tangrubei on 2018/3/1.
 */
@Service
public class UsersServiceImpl implements UsersService {


    @Autowired
    private MongoRepository mongoRepository;


    @Autowired
    private LdapComponent ldapComponent;

    private final static String INDEX_FLAG = "OU=nonobank";

    private final static String DEFAULT_ROLE = "guest";

    private final static boolean DEBUG_FLAG = false;


    private static Map<String, String> permission_map;


    private static Map<String, String> department_map;

    static {
        department_map = new HashMap<String, String>();
        department_map.put("1", "department");
        department_map.put("2", "departmentSecond");
        department_map.put("3", "departmentThird");
        department_map.put("4", "departmentFourth");
        department_map.put("5", "departmentFifth");

        permission_map = new HashMap<String, String>();
        permission_map.put("retrieve", "00001");
        permission_map.put("create", "00010");
        permission_map.put("update", "00100");
        permission_map.put("delete", "01000");
        permission_map.put("exec", "10000");

    }


    /**
     * 将ldapentity转换为usersentity
     *
     * @param ldapUserEntity 传入的ldapuserentity
     * @return
     */
    private static UsersEntity converLdap2entity(LdapUserEntity ldapUserEntity) {

        UsersEntity usersEntity = new UsersEntity();
        usersEntity.setEmail(ldapUserEntity.getMail());
        usersEntity.setUsername(ldapUserEntity.getMailNickname());
        usersEntity.setDate(new Date());
        usersEntity.setLogin(true);
        usersEntity.setNickname(ldapUserEntity.getDisplayName());
        String distinguishedName = ldapUserEntity.getDistinguishedName();

        if (distinguishedName != null && distinguishedName.length() > 0) {
            String[] departments = distinguishedName.substring(0, distinguishedName.indexOf(INDEX_FLAG)).split(",");
            String organization = CharsUtil.strSplitGetIndex(departments[departments.length - 1], "=", 1);
            usersEntity.setOrganization(organization);
            try {
                for (int i = departments.length - 2; i >= 0; i--) {
                    int departmentIndex = departments.length - 1 - i;
                    String methodName = CharsUtil.createSetMethodName(CharsUtil.functionFirstLetterUp.apply(department_map.get(String.valueOf(departmentIndex))));
                    Method method = UsersEntity.class.getMethod(methodName, String.class);
                    String departmentValue = CharsUtil.strSplitGetIndex(departments[i], "=", 1);
                    method.invoke(usersEntity, departmentValue);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), e.getCause() + e.getMessage());
            }

        }

        return usersEntity;

    }


    /**
     * 登陆操作，返回用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public ResponseEntity login(String username, String password, String sessionId) {
//        连接ldap校验用户名密码是否正确
        boolean result = ldapComponent.loginCheck(username, password);

        if (DEBUG_FLAG) {
            result = true;
        }

        if (!result) {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户名或密码错误");
        }
//        依据用户名查询用户信息
        Query userQueryByName = new Query().addCriteria(Criteria.where("username").is(username));
        UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(userQueryByName, UsersEntity.class);

        try {
//            密码加密
            String encryPass = SecretUtil.encryContent2ByteHexStr(password);

//            首次登陆并授予访客权限
            if (usersEntity == null || usersEntity.get_id() == null) {
                LdapUserEntity ldapUserEntity = null;
                if (!DEBUG_FLAG) {
                    ldapUserEntity = ldapComponent.getUserInfo(username);
                } else {
                    ldapUserEntity = new LdapUserEntity();
                }
                usersEntity = converLdap2entity(ldapUserEntity);
                usersEntity.setPassword(encryPass);
                if (DEBUG_FLAG) {
                    usersEntity.setUsername(username);
                }
                mongoRepository.saveEntity(usersEntity);
                grantRole2UserById(usersEntity.get_id(), DEFAULT_ROLE);

            }
//            非首层登陆 判断密码是否更新
            if (!encryPass.equals(usersEntity.getPassword())) {
                usersEntity.setPassword(encryPass);
                mongoRepository.saveEntity(usersEntity);
            }
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new MyUserException(ResponseCode.UNKOWN_ERROR.getCode(), "登陆异常，数据加密出现异常");
        }

        int permission = getPermission(usersEntity);
        usersEntity.setPermission(permission);

//        更新session 并返回权限
        SessionsEntity sessionEntity = new SessionsEntity();
        sessionEntity.set_id(sessionId);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusHours(8);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
        sessionEntity.setExpires(date);
        mongoRepository.saveEntity(sessionEntity);


        return ResponseUtil.success(usersEntity);
    }

    /**
     * 检查session是否失效
     *
     * @param sessionId
     * @return
     */
    @Override
    public ResponseEntity checkSession(String sessionId) {

        SessionsEntity sessionsEntity = (SessionsEntity) mongoRepository.getEntityById(sessionId, SessionsEntity.class);
        if (sessionsEntity == null || sessionsEntity.get_id() == null) {
            return ResponseUtil.error(ResponseCode.VALIDATION_ERROR.getCode(), "session 失效");
        }
        return ResponseUtil.success();
    }

    /**
     * 依据用户名和角色进行授权
     *
     * @param userName 用户名
     * @param role     角色名称
     * @return
     */
    @Override
    public ResponseEntity grantRoleToUser(String userName, String role) {
        grantRole2UserByName(userName, role);

        return ResponseUtil.success();
    }

    /**
     * 依据用户名和角色进行授权
     *
     * @param userName
     * @param roleName
     */
    public void grantRole2UserByName(String userName, String roleName) {
        Query roleQuery = new Query().addCriteria(Criteria.where("username").is(userName));
        UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(roleQuery, UsersEntity.class);
        grantRole2UserById(usersEntity.get_id(), roleName);
    }


    /**
     * 依据用户id和角色名进行授权
     *
     * @param userId
     * @param roleName
     */
    public void grantRole2UserById(String userId, String roleName) {
        Query roleQuery = new Query().addCriteria(Criteria.where("rolename").is(roleName));
        RolesEntity rolesEntity = (RolesEntity) mongoRepository.getOneEntityByWhere(roleQuery, RolesEntity.class);
        UserrolesEntity userrolesEntity = new UserrolesEntity();
        userrolesEntity.setUser(new ObjectId(userId));
        userrolesEntity.setRole(new ObjectId(rolesEntity.get_id()));
        mongoRepository.saveEntity(userrolesEntity);

    }

    /**
     * 获取用户权限
     *
     * @param usersEntity 27949643
     * @return
     */
    public int getPermission(UsersEntity usersEntity) {

        int permissionCode = 0;

        Query userRoleQuery = new Query().addCriteria(Criteria.where("user").is(new ObjectId(usersEntity.get_id())));
        UserrolesEntity userrolesEntity = (UserrolesEntity) mongoRepository.getOneEntityByWhere(userRoleQuery, UserrolesEntity.class);
        if (userrolesEntity == null) {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户未分配角色，无法登陆");
        }
        Query rolepermissionQuery = new Query().addCriteria(Criteria.where("role").is(userrolesEntity.getRole()).and("rights").ne(0));
        List<RolepermissionsEntity> rolepermissionsEntitys = (List<RolepermissionsEntity>) mongoRepository.getEntitysByWhere(rolepermissionQuery, RolepermissionsEntity.class);
        List<String> permissionIds = rolepermissionsEntitys.stream().map(rolePerm -> rolePerm.getPermission().toString()).collect(Collectors.toList());
        Query permissionsQeury = new Query().addCriteria(Criteria.where("_id").in(permissionIds));
        List<PermissionsEntity> permissionsEntities = (List<PermissionsEntity>) mongoRepository.getEntitysByWhere(permissionsQeury, PermissionsEntity.class);

        for (PermissionsEntity p : permissionsEntities) {
            permissionCode = permissionCode|p.getRights();
        }
        return permissionCode;
    }


    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        String name = "CN=喻桥艺,OU=业务测试组,OU=质量控制部QC,OU=技术中心,OU=nonobank,DC=server,DC=nonobank,DC=com";
//        LdapUserEntity ldapUserEntity = new LdapUserEntity();
//        ldapUserEntity.setMail("yu@nonobank.com");
//        ldapUserEntity.setDisplayName("顶顶顶顶");
//        ldapUserEntity.setMailNickname("yu");
//        ldapUserEntity.setDistinguishedName(name);
//        UsersEntity usersEntity = converLdap2entity(ldapUserEntity);
//        System.out.println("ok");
//        System.out.println(Integer.valueOf("00001", 2)|Integer.valueOf("00010", 2));
        System.out.println(Integer.toBinaryString(3));
    }
}
