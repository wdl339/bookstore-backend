package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

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
    public ResponseEntity<Object> getAllUsers(@RequestParam String keyword,
                                                @RequestParam int pageIndex,
                                                @RequestParam int pageSize){
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            return ResponseEntity.ok(userService.getAllUsers(keyword, pageable));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/isAdmin")
    public ResponseEntity<Object> getAdmins(@RequestAttribute("userId") Integer userId){
        try {
            User user = userService.getUserById(userId);
            Map<String, Boolean> isAdmin = new HashMap<>();
            Boolean isAdminBool = (user.getLevel() == 2);
            isAdmin.put("isAdmin", isAdminBool);
            return ResponseEntity.ok(isAdmin);
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}/ban")
    public ResponseEntity<Object> changeBookHide(@RequestAttribute("userId") Integer userId,
                                                 @PathVariable Integer id,
                                                 @RequestBody boolean status){
        try {
            if (userId == id){
                return ResponseEntity.badRequest().body(new ResponseDto(false, "You can't ban yourself"));
            }
            return ResponseEntity.ok(userService.changeUserBan(id, status));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

}
