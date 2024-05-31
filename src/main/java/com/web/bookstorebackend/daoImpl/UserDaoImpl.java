package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.EditProfileDto;
import com.web.bookstorebackend.dto.GetUsersDto;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.repository.UserAuthRepository;
import com.web.bookstorebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

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

    public GetUsersDto findAllUsers(Pageable pageable) {
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();
        long total = userPage.getTotalElements();
        return new GetUsersDto(total, userList);
    }

    public GetUsersDto findAllUsersByNameContaining(String keyword, Pageable pageable) {
        Page<User> userPage = userRepository.findAllByNameContaining(keyword, pageable);
        List<User> userList = userPage.getContent();
        long total = userPage.getTotalElements();
        return new GetUsersDto(total, userList);
    }

    public User findUserByName(String name){
        return userRepository.findByName(name).orElse(null);
    }

    public User findUserById(int id){
        return userRepository.findById(id).orElse(null);
    }

    public Boolean existsIdAndPassword(int id, String password){
        return userAuthRepository.existsByIdAndPassword(id, password);
    }

    public void updateUser(User user, EditProfileDto editProfileDto) {
        user.setName(editProfileDto.getName());
        user.setEmail(editProfileDto.getEmail());
        user.setPhone(editProfileDto.getPhone());
        user.setAddress(editProfileDto.getAddress());
        user.setDescription(editProfileDto.getDescription());
        userRepository.save(user);
    }

    public void changePassword(int userId, String newPassword) {
        UserAuth userAuth = userAuthRepository.findById(userId).orElse(null);
        assert userAuth != null;
        userAuth.setPassword(newPassword);
        userAuthRepository.save(userAuth);
    }

    public void updateBalance(User user, int subtract) {
        user.setBalance(user.getBalance() - subtract);
        userRepository.save(user);
    }

}
