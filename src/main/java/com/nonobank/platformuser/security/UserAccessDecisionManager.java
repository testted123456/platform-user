package com.nonobank.platformuser.security;

import org.springframework.context.annotation.Scope;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Collection;

/**
 * Created by tangrubei on 2018/3/28.
 */
@Component
@Scope("prototype")
public class UserAccessDecisionManager
        implements org.springframework.security.access.AccessDecisionManager {

    @Override
    public void decide(Authentication authentication, Object object,
                       Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException {
    	
    	return;
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
