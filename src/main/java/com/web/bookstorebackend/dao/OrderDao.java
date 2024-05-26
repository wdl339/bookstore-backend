package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Order;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

public interface OrderDao {

    List<Order> findAllOrdersByUserId(Integer userId);

    void addOrder(Order order);

    List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

}
