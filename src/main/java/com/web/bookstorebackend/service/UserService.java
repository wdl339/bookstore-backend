package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.LoginDto;
import com.web.bookstorebackend.dto.RegisterDto;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    TokenUtil tokenUtil;

    public void registerUser(RegisterDto info){

        if (userDao.findUserByName(info.getName()) != null){
            throw new RuntimeException("User already exists");
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setPassword(info.getPassword());
        userDao.saveUserAuth(userAuth);

        User user = new User(info.getName(), info.getEmail(), "/defaultAvatar.jpg", info.getPhone(), "",
                10000000.0, 1, "", userAuth);

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

    public User getUserById(String id){
        return userDao.findUserById(Integer.parseInt(id));
    }
}
