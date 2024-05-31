package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.dto.EditProfileDto;
import com.web.bookstorebackend.dto.GetUsersDto;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserDao {

    void saveUser(User user);

    void saveUserAuth(UserAuth userAuth);

    GetUsersDto findAllUsers(Pageable pageable);

    GetUsersDto findAllUsersByNameContaining(String keyword, Pageable pageable);

    User findUserByName(String name);

    User findUserById(int id);

    Boolean existsIdAndPassword(int id, String password);

    void updateUser(User user, EditProfileDto editProfileDto);

    void changePassword(int UserId, String newPassword);

    void updateBalance(User user, int subtract);

}
