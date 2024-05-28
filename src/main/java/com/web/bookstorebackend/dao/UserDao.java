package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.dto.EditProfileDto;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void saveUserAuth(UserAuth userAuth);

    List<User> findAllUsers();

    List<User> findAllUsersByNameContaining(String keyword);

    User findUserByName(String name);

    User findUserById(int id);

    UserAuth findUserAuthById(int id);

    void updateUser(User user, EditProfileDto editProfileDto);

    void changePassword(UserAuth userAuth, String newPassword);

    void updateBalance(User user, int subtract);

}
