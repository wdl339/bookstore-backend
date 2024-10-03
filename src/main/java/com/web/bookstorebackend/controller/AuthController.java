package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.TimerService;
import com.web.bookstorebackend.service.UserService;
import com.web.bookstorebackend.util.SessionUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

@RestController
@RequestMapping("/api/auth")
//@Scope(value = "session")
public class AuthController {

//    @Autowired
//    private TokenUtil tokenUtil;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private UserService userService;

    @Autowired
    WebApplicationContext applicationContext;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto user,
                                        HttpServletResponse response){
        try {
//            String token = userService.login(user);
//            Cookie cookie = new Cookie("token", token);
//            cookie.setMaxAge(24 * 60 * 60);
//            cookie.setPath("/");
//            cookie.setSecure(true);
//            response.addCookie(cookie);
            User loginUser = userService.login(user);
            sessionUtils.setSession(loginUser);
            TimerService timerService = applicationContext.getBean(TimerService.class);
            timerService.startTimer();

            return ResponseEntity.ok(new ResponseDto(true, "Login success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("logout")
    public ResponseEntity<Object> logout(HttpServletResponse response){
        try {
//            Cookie cookie = new Cookie("token", "");
//            cookie.setMaxAge(0);
//            cookie.setPath("/");
//            response.addCookie(cookie);
            sessionUtils.removeSession();
            TimerService timerService = applicationContext.getBean(TimerService.class);
            String time = timerService.stopTimer();

            return ResponseEntity.ok(new ResponseDto(true,"Logout success. " + time));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }
}