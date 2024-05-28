package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.UserService;
import com.web.bookstorebackend.util.SessionUtils;
import com.web.bookstorebackend.util.TokenUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

//    @Autowired
//    private TokenUtil tokenUtil;

    @Autowired
    private SessionUtils sessionUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto info){
        try {
            System.out.println(info);
            userService.registerUser(info);
            return ResponseEntity.ok(new ResponseDto(true, "Register success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

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

            return ResponseEntity.ok(new ResponseDto(true,"Logout success"));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> getProfile(@RequestAttribute("userId") Integer userId){
        try {
            return ResponseEntity.ok(userService.getUserById(userId));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/profile")
    public ResponseEntity<Object> updateProfile(@RequestAttribute("userId") Integer userId,
                                                @RequestBody EditProfileDto editProfileDto){
        try {
            return ResponseEntity.ok(userService.updateProfile(userId, editProfileDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/password")
    public ResponseEntity<Object> changePassword(@RequestAttribute("userId") Integer userId,
                                                 @RequestBody ChangePasswordDto changePasswordDto){
        try {
            return ResponseEntity.ok(userService.changePassword(userId, changePasswordDto));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/avatar")
    public ResponseEntity<Object> getAvatar(@RequestAttribute("userId") Integer userId){
        try {
            User user = userService.getUserById(userId);
            Map<String, String> avatarResponse = new HashMap<>();
            avatarResponse.put("avatar", user.getAvatar());
            return ResponseEntity.ok(avatarResponse);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PostMapping("/avatar")
    public ResponseEntity<Object> updateAvatar(@RequestAttribute("userId") Integer userId,
                             @RequestParam("avatar") MultipartFile file){
        try {
            String base64Avatar = Base64.getEncoder().encodeToString(file.getBytes());
            base64Avatar = "data:image/png;base64," + base64Avatar;

            return ResponseEntity.ok(userService.updateAvatar(userId, base64Avatar));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/rank")
    public ResponseEntity<Object> getRankUsers(@RequestParam String startTime,
                                              @RequestParam String endTime,
                                              @RequestParam Integer topNumber){
        try {
            return ResponseEntity.ok(userService.getRankUsers(startTime, endTime, topNumber));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUsers(@RequestParam String keyword){
        try {
            return ResponseEntity.ok(userService.getAllUsers(keyword));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

}
