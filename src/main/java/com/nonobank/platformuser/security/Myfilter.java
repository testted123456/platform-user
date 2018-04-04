package com.nonobank.platformuser.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by tangrubei on 2018/3/29.
 */
@Component
public class Myfilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<GrantedAuthority> grantedAuthorities = (List<GrantedAuthority>) authentication.getAuthorities();
//        for (GrantedAuthority ga : grantedAuthorities) {
//            if (ga.getAuthority().equals("ROLE_qa")) {
//                return;
//            }
//        }
//        throw new AccessDeniedException("");
//        return;

    }

    @Override
    public void destroy() {

    }
}
