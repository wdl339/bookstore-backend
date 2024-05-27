package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Order;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

public interface OrderDao {

    List<Order> findAllOrdersByUserId(Integer userId);

    List<Order> findOrdersByUserIdAndKeyword(@Param("userId") Integer userId, @Param("keyword") String keyword);

    void addOrder(Order order);

    List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    List<Order> findOrdersByCreateTimeBetweenAndUserId(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime, @Param("userId") Integer userId);

}
