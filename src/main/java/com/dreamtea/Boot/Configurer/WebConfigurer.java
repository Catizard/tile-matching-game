package com.dreamtea.Boot.Configurer;

import com.dreamtea.Boot.Interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    AuthInterceptor authInterceptor;

    public static String REDIS_SPLIT_SYMBOL = "=";
    public static String REDIS_REMOTETOKEN_PREFIX = "token" + REDIS_SPLIT_SYMBOL;
    public static String REDIS_PLAYERNAME_PREFIX = "name" + REDIS_SPLIT_SYMBOL;
    public static String REDIS_MAP_PREFIX = "map" + REDIS_SPLIT_SYMBOL;
    /*
    TODO
    暂时移除了拦截器
     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/","/login.html","/login")
//                .excludePathPatterns("/genMap","/addBlock")
//                .excludePathPatterns("/img/**","/js/**");
//    }
}
