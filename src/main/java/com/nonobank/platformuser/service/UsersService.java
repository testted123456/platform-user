package com.nonobank.platformuser.service;

import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;

/**
 * Created by tangrubei on 2018/3/1.
 */
public interface UsersService {

    public UsersEntity login(String username, String password,String sessionId);

    public boolean checkSession(String sessionId);

    public boolean grantRoleToUser(String userName,String role);

    public UsersEntity getUsersEntityByName(String userName);


    public UsersEntity getUserBySessionId(String sessionId);
}
