package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.service.UserService;
import com.web.bookstorebackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    TokenUtil tokenUtil;

    public void registerUser(RegisterDto info){

        if (userDao.findUserByName(info.getName()) != null){
            throw new RuntimeException("User already exists");
        }

        UserAuth userAuth = new UserAuth(info.getPassword());
        userDao.saveUserAuth(userAuth);

        User user = new User(info.getName(), info.getEmail(), "/defaultAvatar.jpg", info.getPhone(), "",
                1000000000, 1, "", userAuth);
        userDao.saveUser(user);
    }

    public String login(LoginDto userInfo){

        User user = userDao.findUserByName(userInfo.username);

        if (user == null){
            throw new RuntimeException("Invalid username");
        }

        if (!user.getUserAuth().getPassword().equals(userInfo.password)){
            throw new RuntimeException("Invalid password");
        }

        String userId = String.valueOf(user.getId());

        String role = "ROLE_ADMIN";
        return tokenUtil.getToken(userId, role);
    }

    public User getUserById(int id){
        return userDao.findUserById(id);
    }

    public ResponseDto changePassword(int userId, ChangePasswordDto changePasswordDto){
        UserAuth userAuth = userDao.findUserAuthById(userId);
        if (userAuth == null){
            return new ResponseDto(false, "User not found");
        }
        if (!userAuth.getPassword().equals(changePasswordDto.getOldPassword())){
            return new ResponseDto(false, "Old password is incorrect");
        }

        userDao.changePassword(userAuth, changePasswordDto.getNewPassword());
        return new ResponseDto(true, "Password changed successfully");
    }

    public ResponseDto updateProfile(int userId, EditProfileDto editProfileDto){
        User userInDb = userDao.findUserById(userId);
        if (userInDb == null){
            return new ResponseDto(false, "User not found");
        }

        userDao.updateUser(userInDb, editProfileDto);
        return new ResponseDto(true, "Profile updated successfully");
    }

    public ResponseDto updateAvatar(int userId, String avatar){
        User userInDb = userDao.findUserById(userId);
        if (userInDb == null){
            return new ResponseDto(false, "User not found");
        }

        userInDb.setAvatar(avatar);
        userDao.saveUser(userInDb);
        return new ResponseDto(true, "Avatar updated successfully");
    }
}
