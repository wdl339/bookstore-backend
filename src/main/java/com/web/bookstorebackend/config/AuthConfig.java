package com.web.bookstorebackend.config;

import com.web.bookstorebackend.interceptor.AuthHandlerInterceptor;
import com.web.bookstorebackend.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

//    @Autowired
//    AuthHandlerInterceptor authHandlerInterceptor;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(authHandlerInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/api/user/login", "/api/user/register");
//    }

    @Autowired
    SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(sessionInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/api/auth/login", "/api/auth/register");
    }
}

