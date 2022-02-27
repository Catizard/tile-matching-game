package com.dreamtea.Interceptor;

import com.dreamtea.Service.RedisService;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        Cookie[] cookies = request.getCookies();
        if(cookies == null) {
            response.sendRedirect("/login");
            return false;
        }

        String token = null;
        for (Cookie cookie : cookies) {
            if("token".equals(cookie.getName())) {
                token = cookie.getValue();
                break;
            }
        }

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
