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

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByName(String name){
        return userRepository.findByName(name).orElse(null);
    }

    public UserAuth getAuthByName(String name){
        User user = findByName(name);

        if (user == null) {
            return null;
        } else {
            return user.getUserAuth();
        }
    }

}
