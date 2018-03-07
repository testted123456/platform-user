package com.nonobank.platformuser.controller;

import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by tangrubei on 2018/2/24.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserInfoController {

    private final String KEY_USERNAME="username";
    private final String KEY_PASSWORD = "password";
    private final String KEY_ROLE = "role";

    @Autowired
    private UsersService usersService;


    @RequestMapping(value="login",method = RequestMethod.POST)
    public ResponseEntity login(HttpServletRequest request,@RequestBody Map<String,String> loginMap){
        return usersService.login(loginMap.get(KEY_USERNAME),loginMap.get(KEY_PASSWORD),request.getSession().getId());
    }


    @RequestMapping(value="checkSession",method = RequestMethod.GET)
    public ResponseEntity checkSession(HttpServletRequest request){
        String sessionId = request.getSession().getId();
        return usersService.checkSession(sessionId);
    }

    @RequestMapping(value="grantRole",method = RequestMethod.POST)
    public ResponseEntity grantRoleToUser(@RequestBody Map<String,String> grantMap){
        String username = grantMap.get(KEY_USERNAME);
        String role = grantMap.get(KEY_ROLE);
        return usersService.grantRoleToUser(username,role);
    }



}
