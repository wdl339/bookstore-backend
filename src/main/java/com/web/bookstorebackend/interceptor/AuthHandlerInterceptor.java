package com.web.bookstorebackend.interceptor;

import com.web.bookstorebackend.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.method.HandlerMethod;
import jakarta.servlet.http.Cookie;

import java.util.Map;

@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private TokenUtil tokenUtil;

    @Value("${token.refreshTime}")
    private Long refreshTime;
    @Value("${token.expiresTime}")
    private Long expiresTime;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
//        log.info("=======进入拦截器========");
        // 如果不是映射到方法直接通过,可以访问资源.
        if (!(object instanceof HandlerMethod)) {
            return true;
        }

        String token = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

//        log.info("==============token:" + token);
        if (token == null || token.trim().isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        try {
            Map<String, String> map = tokenUtil.parseToken(token);
            String userId = map.get("userId");
            String userRole = map.get("userRole");
            Integer id = Integer.parseInt(userId);

            httpServletRequest.setAttribute("userId", id);

            long timeOfUse = System.currentTimeMillis() - Long.parseLong(map.get("timeStamp"));

            if (timeOfUse < refreshTime) {
//            log.info("token验证成功");
                return true;
            }

            else if (timeOfUse >= refreshTime && timeOfUse < expiresTime) {
                httpServletResponse.setHeader("token", tokenUtil.getToken(userId,userRole));
//            log.info("token刷新成功");
                return true;
            }

            else {
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

        } catch (Exception e) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}