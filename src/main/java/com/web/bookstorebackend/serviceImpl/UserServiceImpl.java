package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.model.UserAuth;
import com.web.bookstorebackend.service.UserService;
import com.web.bookstorebackend.util.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    TokenUtil tokenUtil;

    public void registerUser(RegisterDto info){

        if (userDao.findUserByName(info.getName()) != null){
            throw new RuntimeException("User already exists");
        }

        UserAuth userAuth = new UserAuth(info.getPassword());
        userDao.saveUserAuth(userAuth);

        User user = new User(info.getName(), info.getEmail(), "/defaultAvatar.jpg", info.getPhone(), "",
                1000000000, 1, "", userAuth);
        userDao.saveUser(user);
    }

    public User login(LoginDto userInfo){
        User user = userDao.findUserByName(userInfo.username);
        if (user == null){
            throw new RuntimeException("Invalid username");
        }
        if (!userDao.existsIdAndPassword(user.getId(), userInfo.password)){
            throw new RuntimeException("Invalid password");
        }
        if (user.isBan()){
            throw new RuntimeException("Your account has been banned");
        }
        return user;
    }

    public User getUserById(int id){
        return userDao.findUserById(id);
    }

    public ResponseDto changePassword(int userId, ChangePasswordDto changePasswordDto){
        if (!userDao.existsIdAndPassword(userId, changePasswordDto.getOldPassword())){
            return new ResponseDto(false, "Old password is incorrect");
        }

        userDao.changePassword(userId, changePasswordDto.getNewPassword());
        return new ResponseDto(true, "Password changed successfully");
    }

    public ResponseDto updateProfile(int userId, EditProfileDto editProfileDto){
        User userInDb = userDao.findUserById(userId);
        if (userInDb == null){
            return new ResponseDto(false, "User not found");
        }

        userDao.updateUser(userInDb, editProfileDto);
        return new ResponseDto(true, "Profile updated successfully");
    }

    public ResponseDto updateAvatar(int userId, String avatar){
        User userInDb = userDao.findUserById(userId);
        if (userInDb == null){
            return new ResponseDto(false, "User not found");
        }

        userInDb.setAvatar(avatar);
        userDao.saveUser(userInDb);
        return new ResponseDto(true, "Avatar updated successfully");
    }

    public List<GetRankUserDto> getRankUsers(String startTime, String endTime, int topNumber){
        Instant start = Objects.equals(startTime, "") ? Instant.EPOCH : Instant.parse(startTime + "Z");
        Instant end = Objects.equals(endTime, "") ? Instant.now() : Instant.parse(endTime + "Z");
        List<Order> orders = orderDao.findOrdersByCreateTimeBetween(start, end);
        Map<Integer, Integer> rankUsers = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = order.getItems();
            for (OrderItem orderItem : orderItems) {
                Integer userId = orderItem.getUserId();
                Integer number = orderItem.getNumber();
                if (rankUsers.containsKey(userId)) {
                    rankUsers.put(userId, rankUsers.get(userId) + number);
                } else {
                    rankUsers.put(userId, number);
                }
            }
        }

        List<GetRankUserDto> result = new ArrayList<>();
        rankUsers.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(topNumber)
                .forEach(entry -> {
                    User user = userDao.findUserById(entry.getKey());
                    result.add(new GetRankUserDto(user.getName(), entry.getValue()));
                });
        return result;
    }

    public List<User> getAllUsers(String keyword){
        if (Objects.equals(keyword, "")){
            return userDao.findAllUsers();
        } else {
            return userDao.findAllUsersByNameContaining(keyword);
        }
    }

    public ResponseDto changeUserBan(int userId, boolean isBan){
        User user = userDao.findUserById(userId);
        if (user == null){
            return new ResponseDto(false, "User not found");
        }

        user.setBan(isBan);
        userDao.saveUser(user);
        return new ResponseDto(true, "User ban changed successfully");
    }

    //    public String login(LoginDto userInfo){
//
//        User user = userDao.findUserByName(userInfo.username);
//
//        if (user == null){
//            throw new RuntimeException("Invalid username");
//        }
//
//        if (!user.getUserAuth().getPassword().equals(userInfo.password)){
//            throw new RuntimeException("Invalid password");
//        }
//
//        String userId = String.valueOf(user.getId());
//
//        String role = "ROLE_ADMIN";
//        return tokenUtil.getToken(userId, role);
//    }
}
