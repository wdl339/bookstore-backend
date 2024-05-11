package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.LoginDto;
import com.web.bookstorebackend.dto.RegisterDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.service.UserSerivce;
import com.web.bookstorebackend.util.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    private UserSerivce userSerivce;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto info){
        try {
            System.out.println(info);
            userSerivce.registerUser(info);
            return ResponseEntity.ok(new ResponseDto(true, "Register success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto user, HttpServletResponse response){
        try {
            String token = userSerivce.login(user);
            Cookie cookie = new Cookie("token", token);
            cookie.setMaxAge(24 * 60 * 60);
            cookie.setPath("/");
            response.addCookie(cookie);

            return ResponseEntity.ok(new ResponseDto(true, token));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    // 仅供测试用
    @PostMapping("/testToken")
    public String testToken(HttpServletRequest request){
        try {
            String token = request.getHeader("token");
            tokenUtil.parseToken(token);
            return "请求成功";
        } catch (Exception e){
            return e.getMessage();
        }
    }
}
