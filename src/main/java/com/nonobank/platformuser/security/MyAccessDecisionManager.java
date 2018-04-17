package com.nonobank.platformuser.security;

import com.nonobank.platformuser.service.UsersService;
import com.nonobank.platformuser.utils.IpAdrressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;


/**
 * Created by tangrubei on 2018/3/28.
 */
@Component
@Scope("prototype")
public class MyAccessDecisionManager
        implements org.springframework.security.access.AccessDecisionManager {

    public static Map urlMap;


    @Value("${ignore.urlPath}")
    String urlPathIgnore;


    @Value("${ignore.ip}")
    String ipIgnore;

    @Autowired
    private UsersService usersService;

    public static final String NO_LOGIN = "no login";


    private static final String ANONYMOUS_USER = "anonymousUser";


    private static final String ROLE_ADMIN = "admin";


    private final static String SYSTEM = "user";



    public boolean checkIgnore(String value,String ignoreConf){
        String[] igonres = ignoreConf.split(",");
        for (String ingore : igonres) {
            if (value.equals(ingore)||value.endsWith(ingore)) {
                return true;
            }
        }
        return false;
    }



    /**
     * 判断是否匿名用户
     * @param authentication
     * @return
     */
    public static boolean isAnonymous(Authentication authentication) {
        if (authentication == null) {
            return true;
        } else {
            return authentication.getPrincipal().toString().equals(ANONYMOUS_USER);

        }


    }

    public void initUrlMap() {
        if (urlMap == null) {
            urlMap = usersService.getUrlMap(SYSTEM);
        }
    }


    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {

//        判断ip是否需要忽略
        String ip = IpAdrressUtil.getIpAdrress(((FilterInvocation) object).getRequest());
        if(checkIgnore(ip,ipIgnore)){
            return ;
        }

//        判断url是否需要忽略
        String url = ((FilterInvocation) object).getRequestUrl();
        if (url.indexOf("?") != -1) {
            url = url.substring(0, url.indexOf("?"));
        }
        if (checkIgnore(url,urlPathIgnore)) {
            return;
        }

//      匿名用户即为非法访问
        if (isAnonymous(authentication)) {
            throw new AccessDeniedException(NO_LOGIN);
        }

//      获取urlmap
        if (urlMap == null || urlMap.keySet().size() == 0) {
            throw new AccessDeniedException("");
        }

//        判断是否有相应的角色
        String needRole = (String) urlMap.get(url);
        for (GrantedAuthority ga : authentication.getAuthorities()) {
            if (ga.getAuthority().equals(ROLE_ADMIN) || needRole.trim().equals(ga.getAuthority().trim())) {
                return;
            }
        }
        throw new AccessDeniedException("");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;

    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;

    }


}
