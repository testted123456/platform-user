package com.nonobank.platformuser.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.nonobank.platformuser.entity.mysqlEntity.Role;
import com.nonobank.platformuser.entity.responseEntity.ResponseCode;
import com.nonobank.platformuser.entity.responseEntity.ResponseEntity;
import com.nonobank.platformuser.entity.responseEntity.ResponseUtil;
import com.nonobank.platformuser.service.RoleService;
import com.nonobank.platformuser.service.UsersService;

@RestController
@EnableAutoConfiguration
@RequestMapping(value = "/role")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoleController {
	
	public static Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	UsersService usersService;
	
	@Autowired
	RoleService roleService;
	
	@GetMapping(value="getAllRoles")
    @ResponseBody
    public ResponseEntity getAllRoles(){
    	logger.info("获取所有角色...");
    	return ResponseUtil.success(roleService.getAll());
    }
	
	@GetMapping(value="getPageRoles")
    @ResponseBody
	public ResponseEntity getPageRoles(@RequestParam int pageIndex, @RequestParam int pageSize){
		logger.info("获取分页角色...");
		return ResponseUtil.success(roleService.getAll(pageIndex, pageSize));
	}

    @PostMapping(value="addRole")
    @ResponseBody
    public ResponseEntity addRole(@RequestBody Role role){
    	logger.info("新增角色：{}", role.getRoleName());
    	role = roleService.save(role);
    	
    	if(null != role){
    		return ResponseUtil.success(role);
    	}else{
    		return ResponseUtil.error(ResponseCode.VALIDATION_ERROR.getCode(), "角色名字已存在...");
    	}
        
    }

    @PostMapping(value="delRole")
    @ResponseBody
    public ResponseEntity delRole(@RequestBody Role role){
    	logger.info("删除角色，id：{}", role.getId());
    	roleService.delete(role);
    	return ResponseUtil.success();
    }

}
