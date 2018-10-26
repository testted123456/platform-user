package com.nonobank.platformuser.component;

import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import com.nonobank.platformuser.remotecontroller.RemoteZuul;
import com.nonobank.platformuser.repository.mysqlRepository.RoleUrlPathRepository;
import com.nonobank.platformuser.service.UsersService;
import com.nonobank.platformuser.utils.RedisUtil;

@Component
public class UrlRoleCache {
	
	public static Logger logger = LoggerFactory.getLogger(UrlRoleCache.class);

	@Autowired
	RoleUrlPathRepository roleUrlPathRepository;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	RemoteZuul remoteZuul;
	
	@Autowired
	RedisUtil redisUtil;

	private Map<String,Map<String, String>> urlMap;

	public Map<String,Map<String, String>> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String,Map<String, String>> urlMap) {
		this.urlMap = urlMap;
	}
	
	/**
	 * url权限保存到redis
	 */
	@EventListener(ApplicationReadyEvent.class)
    public void initUrlMap() {
		logger.info("开始设置url权限...");
		
		urlMap = new HashMap<>();
		Map<String, String> userUrlMap = usersService.getUrlMap("user");
		
		if(null != userUrlMap){
			userUrlMap.forEach((k,v)->{
				redisUtil.set("user", k, v);
			});
		}
		
		Map<String, String> interUrlMap = usersService.getUrlMap("inter");

		if(null != interUrlMap){
			interUrlMap.forEach((k,v)->{
				redisUtil.set("inter", k, v);
			});
		}
		
		Map<String, String> caseUrlMap = usersService.getUrlMap("case");

		if(null != caseUrlMap){
			caseUrlMap.forEach((k,v)->{
				redisUtil.set("case", k, v);
			});
		}
		
		Map<String, String> groupUrlMap = usersService.getUrlMap("group");

		if(null != groupUrlMap){
			groupUrlMap.forEach((k,v)->{
				redisUtil.set("group", k, v);
			});
		}
		
		/*try{
			logger.info("开始同步url权限...");
			Result result = remoteZuul.initUrlMap(urlMap);
			
			if(ResultCode.SUCCESS.getCode().equals(result.getCode())){
				logger.info("同步url权限成功...");
			}else{
				logger.warn("同步url权限失败,{}", result.getMsg());
			}
		}catch(Exception e){
			logger.error("同步url权限失败...");
			e.printStackTrace();
		}*/
    }

}
