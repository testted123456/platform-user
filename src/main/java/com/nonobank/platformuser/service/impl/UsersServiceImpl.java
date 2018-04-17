package com.nonobank.platformuser.service.impl;

import com.nonobank.platformuser.component.LdapComponent;
import com.nonobank.platformuser.component.MyUserException;
import com.nonobank.platformuser.component.RemoteComponent;
import com.nonobank.platformuser.entity.ldapEntity.LdapUserEntity;
import com.nonobank.platformuser.entity.mongoEntity.*;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.RoleUrlPath;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.mysqlEntity.UserRoles;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import com.nonobank.platformuser.repository.MongoRepository;
import com.nonobank.platformuser.repository.mysqlRepository.RoleRepository;
import com.nonobank.platformuser.repository.mysqlRepository.RoleUrlPathRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRolesRepository;
import com.nonobank.platformuser.service.UsersService;
import com.nonobank.platformuser.utils.CharsUtil;
import com.nonobank.platformuser.utils.SecretUtil;
import org.apache.http.HttpException;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by tangrubei on 2018/3/1.
 */
@Service
public class UsersServiceImpl implements UsersService {


    @Autowired
    private MongoRepository mongoRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;


    @Autowired
    private RemoteComponent remoteComponent;


    @Autowired
    private RoleUrlPathRepository roleUrlPathRepository;

    @Autowired
    private LdapComponent ldapComponent;

    @Value("${initRemotes}")
    String initRemotes;


    private final static String INDEX_FLAG = "OU=nonobank";

    private final static String DEFAULT_ROLE = "guest";

    private final static boolean DEBUG_FLAG = false;


    private static Map<String, String> department_map;

    /**
     * 部门层级
     */
    static {
        department_map = new HashMap<String, String>();
        department_map.put("1", "department");
        department_map.put("2", "departmentSecond");
        department_map.put("3", "departmentThird");
        department_map.put("4", "departmentFourth");
        department_map.put("5", "departmentFifth");
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


    private static User createNewUserObj(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setOptstatus((short) 0);
        user.setCreatedTime(LocalDateTime.now());
        user.setCreatedBy("system");
        return user;
    }


    /**
     * 登陆操作，返回用户信息
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @Override
    public UsersEntity login(String username, String password, String sessionId) {
//        连接ldap校验用户名密码是否正确
        boolean result = ldapComponent.loginCheck(username, password);
        if (!result) {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户名或密码错误");
        }
//        依据用户名查询用户信息
        UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateUsersNameQuery.apply(username), UsersEntity.class);

        try {
//            密码加密
            String encryPass = SecretUtil.encryContent2ByteHexStr(password);
//            首次登陆并授予访客权限
            if (usersEntity == null || usersEntity.get_id() == null) {
                LdapUserEntity ldapUserEntity = ldapComponent.getUserInfo(username);
                usersEntity = converLdap2entity(ldapUserEntity);
                usersEntity.setPassword(encryPass);
                mongoRepository.saveEntity(usersEntity);
                grantRole2User(usersEntity, DEFAULT_ROLE);
            }
//            非首次登陆 判断密码是否更新
            if (!encryPass.equals(usersEntity.getPassword())) {
                usersEntity.setPassword(encryPass);
                mongoRepository.saveEntity(usersEntity);
            }
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new MyUserException(ResponseCode.UNKOWN_ERROR.getCode(), "登陆异常，数据加密出现异常");
        }

//        int permission = getPermission(usersEntity);
//        usersEntity.setPermission(permission);
//        更新session 并返回权限
        List<RolesEntity> rolesEntities = this.getRoles(usersEntity);
        usersEntity.setRoles(rolesEntities);
        SessionsEntity sessionEntity = new SessionsEntity();
        sessionEntity.set_id(sessionId);
        sessionEntity.setUsername(username);
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusHours(8);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        java.util.Date date = Date.from(instant);
        sessionEntity.setExpires(date);
        mongoRepository.saveEntity(sessionEntity);
        return usersEntity;
    }

    @Override
    public User login(String username, String password) {
        boolean result = ldapComponent.loginCheck(username, password);
        if (!result) {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户名或密码错误");
        }


//        User user = userRepository.findByUsernameEqualsAndOptstatusNot(username, (short) 2);
        User user = this.getUserByName(username);
        try {
//            密码加密
            String encryPass = SecretUtil.encryContent2ByteHexStr(password);
//            首次登陆
            if (user == null || user.getId() == null) {
                user = createNewUserObj(username, password);
                userRepository.save(user);
            }
//            非首次登陆 判断密码是否更新
            if (!encryPass.equals(user.getPassword())) {
                user.setPassword(encryPass);
                userRepository.save(user);
            }
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new MyUserException(ResponseCode.UNKOWN_ERROR.getCode(), "登陆异常，数据加密出现异常");
        }
        return user;
    }


    /**
     * 检查session是否失效
     *
     * @param sessionId
     * @return
     */
    @Override
    public boolean checkSession(String sessionId) {
        SessionsEntity sessionsEntity = (SessionsEntity) mongoRepository.getEntityById(sessionId, SessionsEntity.class);
        if (sessionsEntity == null || sessionsEntity.get_id() == null) {

            return false;
        }
        return true;
    }


    /**
     * 根据sessionid获取到对应的用户信息
     *
     * @param sessionId
     * @return
     */
    @Override
    public UsersEntity getUserBySessionId(String sessionId) {
        SessionsEntity sessionsEntity = (SessionsEntity) mongoRepository.getEntityById(sessionId, SessionsEntity.class);
        if (sessionsEntity != null) {
            String username = sessionsEntity.getUsername();
            return this.getUsersEntityByName(username);
        }
        return null;

    }


    /**
     * 根据用户名查找用户信息并
     *
     * @param userName 用户名
     * @return
     */
    @Override
    public UsersEntity getUsersEntityByName(String userName) {
        UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateUsersNameQuery.apply(userName), UsersEntity.class);
        List<RolesEntity> rolesEntities = this.getRoles(usersEntity);
        usersEntity.setRoles(rolesEntities);
//        int permission = getPermission(usersEntity);
//        usersEntity.setPermission(permission);
        return usersEntity;

    }


    @Override
    public User getUserByName(String name) {
        User user = userRepository.findByUsernameEqualsAndOptstatusNot(name, (short) 2);
        if (user != null) {
            List<Role> roles = roleRepository.findRoleByUserId(user.getId());
            user.setRoles(roles);

        }
        return user;
    }

    /**
     * 依据用户名和角色进行授权
     *
     * @param userName
     * @param roleName
     */
    @Override
    public boolean grantRoleToUser(String userName, String roleName) {
        /** ldap 赋角色
         UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateUsersNameQuery.apply(userName), UsersEntity.class);
         grantRole2User(usersEntity, roleName);
         **/
        User user = userRepository.findByUsernameEqualsAndOptstatusNot(userName, (short) 2);
        Role role = roleRepository.findRoleByRoleNameEqualsAndOptstatusNot(roleName, (short) 2);
        UserRoles userRoles = new UserRoles();
        userRoles.setUserId(user.getId());
        userRoles.setRoleId(role.getId());
        userRolesRepository.save(userRoles);
        return true;
    }


    /**
     * 依据用户id和角色名进行授权
     *
     * @param usersEntity
     * @param roleName
     */
    public void grantRole2User(UsersEntity usersEntity, String roleName) {
        RolesEntity rolesEntity = (RolesEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateRoleNameQuery.apply(roleName), RolesEntity.class);
        UserrolesEntity userrolesEntity = new UserrolesEntity();
        userrolesEntity.setUser(new ObjectId(usersEntity.get_id()));
        userrolesEntity.setRole(new ObjectId(rolesEntity.get_id()));
        List<RolesEntity> roles = usersEntity.getRoles() == null ? new ArrayList<RolesEntity>() : usersEntity.getRoles();
        roles.add(rolesEntity);
        usersEntity.setRoles(roles);
        mongoRepository.saveEntity(userrolesEntity);
        mongoRepository.saveEntity(usersEntity);

    }


    /**
     * 获取角色列表
     *
     * @param usersEntity
     * @return
     */
    public List<RolesEntity> getRoles(UsersEntity usersEntity) {
        List<RolesEntity> rolesEntities = usersEntity.getRoles();
        if (rolesEntities == null) {
            List<UserrolesEntity> userrolesEntityList = (List<UserrolesEntity>) mongoRepository.getEntitysByWhere(MongoRepository.generateUserRoleByUserIdQuery.apply(usersEntity.get_id()), UserrolesEntity.class);
            rolesEntities = userrolesEntityList.stream().map(userrole -> (RolesEntity) mongoRepository.getEntityById(userrole.getRole().toString(), RolesEntity.class)).collect(Collectors.toList());
            return rolesEntities;
        }
        rolesEntities = rolesEntities.stream().map(rolesEntity -> (RolesEntity) mongoRepository.getEntityById(rolesEntity.getRole(), RolesEntity.class)).collect(Collectors.toList());
        return rolesEntities;


    }


    /**
     * 获取用户权限
     *
     * @param usersEntity
     * @return
     */
    public int getPermission(UsersEntity usersEntity) {

        int permissionCode = 0;

        Query userRoleQuery = new Query().addCriteria(Criteria.where("user").is(new ObjectId(usersEntity.get_id())));

        List<UserrolesEntity> userrolesEntityList = (List<UserrolesEntity>) mongoRepository.getEntitysByWhere(userRoleQuery, UserrolesEntity.class);

        if (userrolesEntityList == null) {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户未分配角色，无法登陆");
        }
        for (UserrolesEntity userrolesEntity : userrolesEntityList) {
            Query rolepermissionQuery = new Query().addCriteria(Criteria.where("role").is(userrolesEntity.getRole()).and("rights").ne(0));
            List<RolepermissionsEntity> rolepermissionsEntitys = (List<RolepermissionsEntity>) mongoRepository.getEntitysByWhere(rolepermissionQuery, RolepermissionsEntity.class);
            List<String> permissionIds = rolepermissionsEntitys.stream().map(rolePerm -> rolePerm.getPermission().toString()).collect(Collectors.toList());
            Query permissionsQeury = new Query().addCriteria(Criteria.where("_id").in(permissionIds));
            List<PermissionsEntity> permissionsEntities = (List<PermissionsEntity>) mongoRepository.getEntitysByWhere(permissionsQeury, PermissionsEntity.class);

            for (PermissionsEntity p : permissionsEntities) {
                permissionCode = permissionCode | p.getRights();
            }
        }

        return permissionCode;
    }
    @Override
    public Map<String, String> getUrlMap(String system) {
        List<RoleUrlPath> roleUrlPaths = roleUrlPathRepository.findBySystemEqualsAndOptstatusNot(system, (short) 2);
        if (roleUrlPaths == null || roleUrlPaths.size() == 0) {
            return null;
        }
        Map<String, String> roleUrlMap = new HashMap<>();
        roleUrlPaths.forEach(roleUrlPath -> {
            roleUrlMap.put(roleUrlPath.getUrlPath(), roleUrlPath.getRoleName());
        });
        return roleUrlMap;
    }

    @Override
    public void callRemoteServiceInitUrlMap(){
        String[] initRemoteUrlsList = initRemotes.split(",");
        for(String remoteUrl:initRemoteUrlsList){
            try {
                remoteComponent.initRemoteSystemRoleUrl(remoteUrl);
            } catch (IOException|HttpException e) {
                e.printStackTrace();
            }

        }



    }
}
