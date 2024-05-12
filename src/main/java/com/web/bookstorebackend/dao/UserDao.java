package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.repository.UserAuthRepository;
import com.web.bookstorebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserDao {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public void saveUserAuth(UserAuth userAuth) {
        userAuthRepository.save(userAuth);
    }

    public User findUserByName(String name){
        return userRepository.findByName(name).orElse(null);
    }

    public User findUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

}
