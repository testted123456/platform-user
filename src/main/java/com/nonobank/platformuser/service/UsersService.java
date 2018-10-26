package com.nonobank.platformuser.service;

import java.util.List;
import java.util.Map;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.User;

/**
 * Created by tangrubei on 2018/3/1.
 */
public interface UsersService {

	public User resetPasswd(String userName, String initPasswd, String newPasswd);

    public User login(String username, String password);

    public boolean grantRoleToUser(String userName,String role);

    public User getUserByName(String userName);
    
    public List<Role> getAllRoles();
    
    public Role addRole(Role role);
    
    public void delRole(Role role);
    
    public Role getRoleByName(String name);

    public Map<String,String> getUrlMap(String system);
    
    public void callRemoteServiceInitUrlMap();
    
    public List<User> getAllUsers();
    
    public List<Map<String, Object>> searchByname(String name);
    
    public List<Map<String, Object>> findAllUsers();
    
    public List<Map<String, Object>>  findUsersByRoleName(String rolename);
    
    public List<JSONObject> getAllPrivileges();

}
