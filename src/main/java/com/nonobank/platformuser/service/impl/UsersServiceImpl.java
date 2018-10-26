package com.nonobank.platformuser.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.platformuser.component.LdapComponent;
import com.nonobank.platformuser.component.MyUserException;
import com.nonobank.platformuser.component.RemoteComponent;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.RoleUrlPath;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.mysqlEntity.UserRoles;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import com.nonobank.platformuser.repository.mysqlRepository.RoleRepository;
import com.nonobank.platformuser.repository.mysqlRepository.RoleUrlPathRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRolesRepository;
import com.nonobank.platformuser.service.UsersService;

/**
 * Created by tangrubei on 2018/3/1.
 */
@Service
public class UsersServiceImpl implements UsersService {

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

    private static User createNewUserObj(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setOptstatus((short) 0);
        user.setCreatedTime(LocalDateTime.now());
        user.setCreatedBy("system");
        return user;
    }

    private boolean checkAdmin(String username, String password){
        User user = this.getUserByName(username);
        if(user.getPassword().equals(password)){
            return true;
        }
        return false;
    }
    
    public User resetPasswd(String userName, String initPasswd, String newPasswd) {
    	 User user = this.getUserByName(userName);
         
         if(user.getPassword().equals(initPasswd)){
        	 user.setPassword(newPasswd);
        	 user.setPasswodChanged(true);
        	 userRepository.save(user);
             return user;
         }else{
             throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "老密码错误");
         }
    }

    /**
     * 用户登录不走ldap，直接查数据库
     */
    @Override
    public User login(String username, String password) {
        User user = this.getUserByName(username);
        
        if(user.getPassword().equals(password)){
            return user;
        }else{
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户名或密码错误");
        }

        //登录不走ldap
        /**
        boolean result = ldapComponent.loginCheck(username, password);
        if (!result) {
            throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "用户名或密码错误");
        }
        // User user = userRepository.findByUsernameEqualsAndOptstatusNot(username, (short) 2);
        User user = this.getUserByName(username);
        try {
            // 密码加密
            String encryPass = SecretUtil.encryContent2ByteHexStr(password);
            // 首次登陆
            if (user == null || user.getId() == null) {
                user = createNewUserObj(username, password);
                LdapUserEntity ldapUserEntity = ldapComponent.getUserInfo(username);
                user.setNickname(ldapUserEntity.getDisplayName());
                userRepository.save(user);
            }
            // 非首次登陆 判断密码是否更新
            if (!encryPass.equals(user.getPassword())) {
                user.setPassword(encryPass);
                userRepository.save(user);
            }
        } catch (NoSuchPaddingException | InvalidKeyException | NoSuchAlgorithmException | IllegalBlockSizeException | BadPaddingException | InvalidAlgorithmParameterException e) {
            throw new MyUserException(ResponseCode.UNKOWN_ERROR.getCode(), "登陆异常，数据加密出现异常");
        }
        return user;
        **/
    }


    /**
     * 检查session是否失效
     *
     * @param sessionId
     * @return
     */
    /*@Override
    public boolean checkSession(String sessionId) {
        SessionsEntity sessionsEntity = (SessionsEntity) mongoRepository.getEntityById(sessionId, SessionsEntity.class);
        if (sessionsEntity == null || sessionsEntity.get_id() == null) {

            return false;
        }
        return true;
    }*/

    /**
     * 根据sessionid获取到对应的用户信息
     *
     * @param sessionId
     * @return
     */
    /*@Override
    public UsersEntity getUserBySessionId(String sessionId) {
        SessionsEntity sessionsEntity = (SessionsEntity) mongoRepository.getEntityById(sessionId, SessionsEntity.class);
        if (sessionsEntity != null) {
            String username = sessionsEntity.getUsername();
            return this.getUsersEntityByName(username);
        }
        return null;
    }*/

    /**
     * 根据用户名查找用户信息并
     *
     * @param userName 用户名
     * @return
     */
   /* @Override
    public UsersEntity getUsersEntityByName(String userName) {
        UsersEntity usersEntity = (UsersEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateUsersNameQuery.apply(userName), UsersEntity.class);
        List<RolesEntity> rolesEntities = this.getRoles(usersEntity);
        usersEntity.setRoles(rolesEntities);
//        int permission = getPermission(usersEntity);
//        usersEntity.setPermission(permission);
        return usersEntity;
    }*/


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
        UserRoles userRoles = userRolesRepository.findByUserId(user.getId());
        if(null == userRoles){
        	userRoles = new UserRoles();
        }
        userRoles.setUserId(user.getId());
        userRoles.setRoleId(role.getId());
        userRoles.setOptstatus((short)0);
        
        userRolesRepository.save(userRoles);
        return true;
    }


    /**
     * 依据用户id和角色名进行授权
     *
     * @param usersEntity
     * @param roleName
     */
    /*public void grantRole2User(UsersEntity usersEntity, String roleName) {
        RolesEntity rolesEntity = (RolesEntity) mongoRepository.getOneEntityByWhere(MongoRepository.generateRoleNameQuery.apply(roleName), RolesEntity.class);
        UserrolesEntity userrolesEntity = new UserrolesEntity();
        userrolesEntity.setUser(new ObjectId(usersEntity.get_id()));
        userrolesEntity.setRole(new ObjectId(rolesEntity.get_id()));
        List<RolesEntity> roles = usersEntity.getRoles() == null ? new ArrayList<RolesEntity>() : usersEntity.getRoles();
        roles.add(rolesEntity);
        usersEntity.setRoles(roles);
        mongoRepository.saveEntity(userrolesEntity);
        mongoRepository.saveEntity(usersEntity);

    }*/

    /**
     * 获取角色列表
     *
     * @param usersEntity
     * @return
     */
    /*public List<RolesEntity> getRoles(UsersEntity usersEntity) {
        List<RolesEntity> rolesEntities = usersEntity.getRoles();
        if (rolesEntities == null) {
            List<UserrolesEntity> userrolesEntityList = (List<UserrolesEntity>) mongoRepository.getEntitysByWhere(MongoRepository.generateUserRoleByUserIdQuery.apply(usersEntity.get_id()), UserrolesEntity.class);
            rolesEntities = userrolesEntityList.stream().map(userrole -> (RolesEntity) mongoRepository.getEntityById(userrole.getRole().toString(), RolesEntity.class)).collect(Collectors.toList());
            return rolesEntities;
        }
        rolesEntities = rolesEntities.stream().map(rolesEntity -> (RolesEntity) mongoRepository.getEntityById(rolesEntity.getRole(), RolesEntity.class)).collect(Collectors.toList());
        return rolesEntities;
    }*/


    /**
     * 获取用户权限
     *
     * @param usersEntity
     * @return
     */
    /*public int getPermission(UsersEntity usersEntity) {

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
    }*/
    
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

	@Override
	public List<Role> getAllRoles() {
		return	roleRepository.findAll();
	}

	@Override
	public Role addRole(Role role) {
		Integer id = role.getId();
		String name = role.getRoleName();
		Role r = getRoleByName(name);
		
		if(null == r || r.getId().equals(id)){
			role.setOptstatus((short)0);
			role = roleRepository.save(role);
		}else{
			throw new MyUserException(ResponseCode.VALIDATION_ERROR.getCode(), "角色名称已存在！");
		}
		
		return role;
	}


	@Override
	public void delRole(Role role) {
		roleRepository.delete(role.getId());
	}

	@Override
	public Role getRoleByName(String name) {
		return roleRepository.findByRoleName(name);
	}

	@Override
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		
		users.forEach(x->{
			
			List<Role> roles = roleRepository.findRoleByUserId(x.getId());
			x.setRoles(roles);
		});
		
		return users;
	}

	@Override
	public List<Map<String, Object>> searchByname(String name) {
		List<Map<String, Object>> listOfUsers = new ArrayList<>();
		List<Object[]> list = userRepository.findByUsernameLike(name);
		
		list.forEach(x->{
			Map<String, Object> map = new HashMap<>();
			map.put("id", x[0]);
			map.put("username", x[1]);
			map.put("nickname", x[2]);
			map.put("role", x[4]);
//			List<Object> roles = new ArrayList<>();
//			roles.add(x[3]);
//			map.put("roles", roles);
			listOfUsers.add(map);
		});
		
		return listOfUsers;
	}
	
	@Override
	public List<Map<String, Object>> findAllUsers(){
		List<Map<String, Object>> listOfUsers = new ArrayList<>();
		List<Object[]> list = userRepository.findAllUsers();
		
		list.forEach(x->{
			Map<String, Object> map = new HashMap<>();
			map.put("id", x[0]);
			map.put("username", x[1]);
			map.put("nickname", x[2]);
			map.put("role", x[4]);
//			List<Object> roles = new ArrayList<>();
//			roles.add(x[3]);
//			map.put("roles", roles);
			listOfUsers.add(map);
		});
		
		return listOfUsers;
	}

	@Override
	public List<JSONObject> getAllPrivileges() {
		List<RoleUrlPath> roleUrlPathes = roleUrlPathRepository.findAll();
		
		Map<String, Map<String, List<RoleUrlPath>>> maps =
		roleUrlPathes.stream().collect(Collectors.groupingBy(RoleUrlPath::getSystem, Collectors.groupingBy(RoleUrlPath::getUrlPath)));
		
		List<JSONObject> list = new ArrayList<>();
		
		maps.forEach((k,v)->{
			final String system = k;
			
			v.forEach((k1,v1)->{
				String url = k1;
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("system", system);
				jsonObj.put("url", url);
				 List<Integer> roles = v1.stream().map(x->{
					return x.getRoleId();
				}).collect(Collectors.toList());
				jsonObj.put("roles", roles);
				list.add(jsonObj);
			});
		});
		
		return list;
	}


	@Override
	public List<Map<String, Object>> findUsersByRoleName(String rolename) {
		List<Map<String, Object>> listOfUsers = new ArrayList<>();
		List<Object[]> list = userRepository.findUsersByRoleName(rolename);
		
		list.forEach(x->{
			Map<String, Object> map = new HashMap<>();
			map.put("email", x[0]);
			map.put("nickname", x[1]);
			listOfUsers.add(map);
		});
		
		return listOfUsers;
	}

}
