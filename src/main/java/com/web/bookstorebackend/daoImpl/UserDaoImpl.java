package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.EditProfileDto;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.repository.UserAuthRepository;
import com.web.bookstorebackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public List<User> findAllUsersByNameContaining(String keyword){
        return userRepository.findAllByNameContaining(keyword);
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
