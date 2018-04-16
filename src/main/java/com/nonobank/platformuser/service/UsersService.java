package com.nonobank.platformuser.service;

import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;

import java.util.Map;

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



    public UsersEntity getUserBySessionId(String sessionId);

    public Map<String,String> getUrlMap(String system);


    public void callRemoteServiceInitUrlMap();
}
