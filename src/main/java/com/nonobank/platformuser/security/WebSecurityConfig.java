package com.nonobank.platformuser.security;

import com.nonobank.platformuser.component.RemoteComponent;
import com.nonobank.platformuser.service.UsersService;
import com.nonobank.platformuser.service.impl.UsersServiceImpl;
import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.rmi.Remote;


/**
 * <Description> <br>
 *
 * @author henley<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2017年1月13日 <br>
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserAccessDecisionManager userAccessDecisionManager;


    @Autowired
    UsersService usersService;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.csrf().disable();

       /* myAccessDecisionManager.initUrlMap();
        http.csrf().disable().authorizeRequests()
                .antMatchers("/**").authenticated().accessDecisionManager(myAccessDecisionManager);
        http.exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint());*/
    }

    /**
     * 登陆成功后的处理
     */
    public static class RestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

        @Override
        public void onAuthenticationSuccess(HttpServletRequest request,
                                            HttpServletResponse response, Authentication authentication)
                throws ServletException, IOException {
            clearAuthenticationAttributes(request);
        }
    }


//    /**
//     * 权限不通过的处理
//     */
//    public static class MyAccessDeniedHandler implements AccessDeniedHandler {
//
//        @Override
//        public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
//            response.setStatus(200);
//            response.setHeader("content-type", "application/json;charset=UTF-8");
//            OutputStream os = response.getOutputStream();
//            ObjectOutputStream oos = new ObjectOutputStream(os);
//            oos.writeObject("{\"code\":1008, \"msg\":\"insuffcient rights\"}");
//            oos.close();
//        }
//
//    }


    
    /**
     * 权限不通过的处理
     */
    public static class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException {


            /*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if (UserAccessDecisionManager.isAnonymous(authentication)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Authentication Failed: No login!");
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Authentication Failed: " + authException.getMessage());
            }*/
        }
    }
}