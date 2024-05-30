package com.web.bookstorebackend.config;

import com.web.bookstorebackend.interceptor.IdentityInterceptor;
import com.web.bookstorebackend.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IdentityConfig implements WebMvcConfigurer {

    @Autowired
    IdentityInterceptor identityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(identityInterceptor)
                .addPathPatterns("/api/books/all","/api/books/{id}/cover",
                        "/api/books/{id}/hide","/api/books/edit/{id}",
                        "/api/books/create","/api/orders/all",
                        "/api/user/all", "/api/user/{id}/ban");
    }
}