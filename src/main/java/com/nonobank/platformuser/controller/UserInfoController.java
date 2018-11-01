package com.nonobank.platformuser.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.nonobank.platformuser.component.UrlRoleCache;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.mysqlEntity.RoleUrlPath;
import com.nonobank.platformuser.entity.mysqlEntity.User;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseUtil;
import com.nonobank.platformuser.service.UsersService;

/**
 * Created by tangrubei on 2018/2/24.
 */
@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserInfoController {
	
	public static Logger logger = LoggerFactory.getLogger(UserInfoController.class);

    private final static String KEY_USERNAME = "username";
    private final static String KEY_NICKNAME = "nickname";
    private final static String KEY_PASSWORD = "password";
    private final static String KEY_ROLE = "role";
    private final static String PREFIX_ROLE = "ROLE_";

    @Autowired
    private UsersService usersService;
    
    @Autowired
    private UrlRoleCache urlRoleCache;

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

    @RequestMapping(value = "resetPasswd", method = RequestMethod.POST)
    public ResponseEntity resetPasswd( @RequestBody Map<String, String> map) {
    	String userName = map.get("userName");
    	String initPasswd = map.get("initPasswd");
    	String newPasswd = map.get("newPasswd");
    	User user = usersService.resetPasswd(userName, initPasswd, newPasswd);
    	return ResponseUtil.success(user);
    }
    
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity login(HttpServletRequest request, @RequestBody Map<String, String> loginMap) {
        User user = usersService.login(loginMap.get(KEY_USERNAME), loginMap.get(KEY_PASSWORD));
        setContextValue(user, request);
        
        if(user.getPasswodChanged().equals(false)) {
        	return ResponseUtil.error(ResponseCode.INITPASSWD_ERROR.getCode(), ResponseCode.INITPASSWD_ERROR.getMsg(), user);
        }else {
        	 return ResponseUtil.success(user);
        }
       
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

    /*@RequestMapping(value = "checkSession", method = RequestMethod.GET)
    public ResponseEntity checkSession(HttpServletRequest request) {

        String sessionId = request.getSession().getId();
        boolean f = usersService.checkSession(sessionId);
        if (f) {
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error(ResponseCode.VALIDATION_ERROR.getCode(), "session 失效");
        }
    }*/

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
        logger.info("用户：{}", userName);
        User user = usersService.getUserByName(userName);
        
        if(null == user) {
        	return ResponseUtil.error(ResponseCode.DB_ERROR.getCode(), "用户不存在！");
        }
        return ResponseUtil.success(user);
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index(@RequestParam String word) {
        return "hello " + word;
    }

    /**
     * 给用户添加角色
     * @param grantMap
     * @return
     */
    @RequestMapping(value = "grantRole", method = RequestMethod.POST)
    public ResponseEntity grantRoleToUser(@RequestBody Map<String, String> grantMap) {
        String username = grantMap.get(KEY_USERNAME);
        String role = grantMap.get(KEY_ROLE);
        String nickname = grantMap.get(KEY_NICKNAME);
        
        User user = usersService.createNewUser(username, nickname, "e10adc3949ba59abbe56e057f20f883e");
        		
        boolean f = usersService.grantRoleToUser(username, role);
        
        return ResponseUtil.success();
        /*
        if (f) {
            usersService.callRemoteServiceInitUrlMap();
            return ResponseUtil.success();
        } else {
            return ResponseUtil.error(ResponseCode.UNKOWN_ERROR.getCode(), "权限赋值失败");
        }*/
    }
    
    @PostMapping(value="addRoleUrlPath")
    @ResponseBody
    public ResponseEntity addRoleUrlPath(@RequestBody JSONObject jsonObj){
    	logger.info("begin to add roleUrlPath...");
    	
    	String system = jsonObj.getString("system");
    	String url = jsonObj.getString("url");
    	JSONArray roles = jsonObj.getJSONArray("roles");
    	
    	List<Integer> roleIds = roles.stream().map(String::valueOf).map(Integer::parseInt).collect(Collectors.toList());
    	
    	List<RoleUrlPath> list = usersService.addRoleUrlPath(system, url, roleIds);
    	
    	/*roles.forEach(x->{
    		Integer roleId = Integer.parseInt(String.valueOf(x));
    		usersService.addRoleUrlPath(system, url, roleId);
    	});*/
    	
    	return ResponseUtil.success(list);
    }
    
    @GetMapping(value="refreshRoleUrlPath")
    @ResponseBody
    public ResponseEntity refreshRoleUrlPath(){
    	logger.info("begin to refresh roleUrlPath...");
    	urlRoleCache.removeUrlMap();
    	urlRoleCache.initUrlMap();
    	return ResponseUtil.success();
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
    	List<Map<String, Object>> users = usersService.searchByname(name);
    	return ResponseUtil.success(users);
    }
    
    @GetMapping(value="getAllPrivileges")
    @ResponseBody
	public ResponseEntity getAllPrivileges() {
    	return ResponseUtil.success(usersService.getAllPrivileges());
	}
    
    @GetMapping(value="getUsersByRole")
    @ResponseBody
    public ResponseEntity getUsersByRole(@RequestParam String rolename){
    	List<Map<String, Object>> listOfUsers = usersService.findUsersByRoleName(rolename);
    	return ResponseUtil.success(listOfUsers);
    }
    
}
