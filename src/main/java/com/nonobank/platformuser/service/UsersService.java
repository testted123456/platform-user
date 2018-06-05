package com.nonobank.platformuser.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.RoleUrlPath;
import com.nonobank.platformuser.entity.mysqlEntity.User;

/**
 * Created by tangrubei on 2018/3/1.
 */
public interface UsersService {

    public UsersEntity login(String username, String password,String sessionId);

    public User login(String username, String password);

    public boolean checkSession(String sessionId);

    public boolean grantRoleToUser(String userName,String role);

    public UsersEntity getUsersEntityByName(String userName);

    public User getUserByName(String userName);
    
    public List<Role> getAllRoles();
    
    public Role addRole(Role role);
    
    public void delRole(Role role);
    
    public Role getRoleByName(String name);

    public UsersEntity getUserBySessionId(String sessionId);

    public Map<String,String> getUrlMap(String system);


    public void callRemoteServiceInitUrlMap();
    
    public List<User> getAllUsers();
    
    public List<Map<String, Object>> searchByname(String name);
    
    public List<Map<String, Object>> findAllUsers();
    
    public List<Map<String, Object>>  findUsersByRoleName(String rolename);
    
    public List<JSONObject> getAllPrivileges();

}
