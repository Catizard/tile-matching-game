package com.dreamtea.Boot.Interceptor;

import com.dreamtea.Boot.Service.RedisService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = response.getHeader("Authorization");
        if(StringUtil.isNullOrEmpty(token)) {
            response.sendRedirect("/login");
            return false;
        }

        String loginName = (String) redisService.get(token);
        if(StringUtil.isNullOrEmpty(loginName)) {
            response.sendRedirect("/login");
            return false;
        }

        String authToken = (String) redisService.get(loginName);
        if(StringUtil.isNullOrEmpty(authToken)) {
            response.sendRedirect("/login");
            return false;
        }
        if(!authToken.equals(token)) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
