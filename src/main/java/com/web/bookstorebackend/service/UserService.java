package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    void registerUser(RegisterDto info);

    User login(LoginDto userInfo);
    User getUserById(int id);

    ResponseDto changePassword(int userId, ChangePasswordDto changePasswordDto);
    ResponseDto updateProfile(int userId, EditProfileDto editProfileDto);

    ResponseDto updateAvatar(int userId, String avatar);

    List<GetRankUserDto> getRankUsers(String startTime, String endTime, int topNumber);

    GetUsersDto getAllUsers(String keyword, Pageable pageable);

    ResponseDto changeUserBan(int userId, boolean isBan);
}
