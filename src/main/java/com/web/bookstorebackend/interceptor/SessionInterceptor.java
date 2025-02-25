package com.web.bookstorebackend.interceptor;

import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.UserService;
import com.web.bookstorebackend.util.SessionUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) {
        try{

            if (!(object instanceof HandlerMethod)) {
                return true;
            }

            HttpSession session = request.getSession(false);
            Integer userId = (Integer) session.getAttribute("userId");

            if (userId == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            } else {
                User user = userService.getUserById(userId);
                if (user == null || user.isBan()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                }
                request.setAttribute("userId", userId);
                return true;
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
    }
}
