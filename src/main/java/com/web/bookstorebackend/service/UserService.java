package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.User;

public interface UserService {
    void registerUser(RegisterDto info);

    String login(LoginDto userInfo);
    User getUserById(int id);

    ResponseDto changePassword(int userId, ChangePasswordDto changePasswordDto);
    ResponseDto updateProfile(int userId, EditProfileDto editProfileDto);

    ResponseDto updateAvatar(int userId, String avatar);
}
