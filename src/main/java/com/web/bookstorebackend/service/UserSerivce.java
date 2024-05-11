package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.LoginDto;
import com.web.bookstorebackend.dto.RegisterDto;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.repository.UserAuthRepository;
import com.web.bookstorebackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSerivce {

    @Autowired
    private UserDao userDao;

    @Autowired
    TokenUtil tokenUtil;

    public void registerUser(RegisterDto info){

        if (userDao.findByName(info.getName()) != null){
            throw new RuntimeException("User already exists");
        }

        UserAuth userAuth = new UserAuth();
        userAuth.setPassword(info.getPassword());

        User user = new User(info.getName(), info.getEmail(), "", info.getPhone(), "",
                10000000.0, 1, "", userAuth);

        userDao.save(user);
    }

    public String login(LoginDto user){

        if (!validateUser(user)){
            throw new RuntimeException("Invalid username or password");
        }

        String role = "ROLE_ADMIN";
        return tokenUtil.getToken(user.username,role);
    }

    public boolean validateUser(LoginDto info){
        String username = info.username;
        UserAuth userAuth = userDao.getAuthByName(username);

        return userAuth != null && userAuth.getPassword().equals(info.password);
    }
}
