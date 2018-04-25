package com.nonobank.platformuser.controller;

import com.alibaba.fastjson.JSONObject;
import com.nonobank.platformuser.entity.mongoEntity.RolesEntity;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.RoleUrlPath;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import com.nonobank.platformuser.entity.mongoEntity.UsersEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.repository.mysqlRepository.RoleUrlPathRepository;
import com.nonobank.platformuser.repository.mysqlRepository.UserRepository;
import com.nonobank.platformuser.service.UsersService;
import com.nonobank.platformuser.entity.responseEntity.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangrubei on 2018/2/24.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
public class UserInfoController {

    private final static String KEY_USERNAME = "username";
    private final static String KEY_PASSWORD = "password";
    private final static String KEY_ROLE = "role";
    private final static String PREFIX_ROLE = "ROLE_";


    @Autowired
    private UsersService usersService;


//    @Autowired
//    private RoleUrlPathRepository roleUrlPathRepository;


    private void setContextValue(User user, HttpServletRequest request) {
        int roleSize = user.getRoles() != null ? user.getRoles().size() : 0;
        String[] roles = new String[roleSize];
        for (int i = 0; i < roles.length; i++) {
            Role role = user.getRoles().get(i);
            roles[i] = role.getRoleName();
        }
        List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession session = request.getSession();
        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
    }


    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity login(HttpServletRequest request, @RequestBody Map<String, String> loginMap) {
        User user = usersService.login(loginMap.get(KEY_USERNAME), loginMap.get(KEY_PASSWORD));
        setContextValue(user, request);
        return ResponseUtil.success(user);
    }


    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ResponseEntity logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
        return ResponseUtil.success();
    }

    @RequestMapping(value = "reoladRoles", method = RequestMethod.GET)
    public ResponseEntity reoladRoles(HttpServletRequest request, @RequestParam String username) {
        User user = usersService.getUserByName(username);
        setContextValue(user, request);
        return ResponseUtil.success(user);
    }


    @RequestMapping(value = "checkSession", method = RequestMethod.GET)
    public ResponseEntity checkSession(HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        boolean f = usersService.checkSession(sessionId);
        if (f) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error(ResponseCode.VALIDATION_ERROR.getCode(), "session 失效");
        }
    }

    @RequestMapping(value = "getUesrSessionId", method = RequestMethod.GET)
    public String getSessionId(HttpServletRequest request) {
        return request.getSession().getId();
    }


    @RequestMapping(value = "getRoleUrlPathBySystem", method = RequestMethod.GET)
    public ResponseEntity getRoleUrlPathBySystem(@RequestParam String system) {
        Map<String, String> remap = usersService.getUrlMap(system);
        return ResponseUtil.success(remap);
    }

    @RequestMapping(value = "getUserBySession", method = RequestMethod.GET)
    public ResponseEntity getUserBySession() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = String.valueOf(authentication.getPrincipal());
        User user = usersService.getUserByName(userName);
        return ResponseUtil.success(user);
    }



    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@RequestParam String word) {
        return "hello " + word;
    }

    @RequestMapping(value = "grantRole", method = RequestMethod.POST)
    public ResponseEntity grantRoleToUser(@RequestBody Map<String, String> grantMap) {
        String username = grantMap.get(KEY_USERNAME);
        String role = grantMap.get(KEY_ROLE);
        boolean f = usersService.grantRoleToUser(username, role);
        if (f) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error(ResponseCode.UNKOWN_ERROR.getCode(), "权限赋值失败");
        }
    }
    
    @GetMapping(value="getAllRoles")
    @ResponseBody
    public ResponseEntity getAllRoles(){
    	return ResponseUtil.success(usersService.getAllRoles());
    }

    @PostMapping(value="addRole")
    @ResponseBody
    public ResponseEntity addRole(@RequestBody Role role){
    		role = usersService.addRole(role);
        	return ResponseUtil.success(role);
    }

    @PostMapping(value="delRole")
    @ResponseBody
    public ResponseEntity delRole(@RequestBody Role role){
    	usersService.delRole(role);
    	return ResponseUtil.success();
    }
    
    @GetMapping(value="getAllUsers")
    @ResponseBody
    public ResponseEntity getAllUsers(){
    	List<Map<String, Object>> users = 
    			usersService.findAllUsers();
//    			usersService.getAllUsers();
    	return ResponseUtil.success(users);
    }
    
    @GetMapping(value="searchByName")
    @ResponseBody
    public ResponseEntity searchByName(@RequestParam String name){
    	List<User> users = usersService.searchByname("%" + name + "%");
    	return ResponseUtil.success(users);
    }
    
    @GetMapping(value="getAllPrivileges")
    @ResponseBody
	public ResponseEntity getAllPrivileges() {
    	return ResponseUtil.success(usersService.getAllPrivileges());
	}
    
}
