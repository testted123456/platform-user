package com.nonobank.platformuser.service;

import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;

/**
 * Created by tangrubei on 2018/3/1.
 */
public interface UsersService {

    public ResponseEntity login(String username, String password,String sessionId);

    public ResponseEntity checkSession(String sessionId);

    public ResponseEntity grantRoleToUser(String userName,String role);
}
