package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.time.Instant;
import java.util.List;

public interface OrderDao {

    List<Order> findAllOrdersByUserId(Integer userId);

    List<Order> findAllOrders();

    List<Order> findOrdersByUserIdAndKeyword(@Param("userId") Integer userId, @Param("keyword") String keyword);

    List<Order> findOrdersByKeyword(@Param("keyword") String keyword);

    int addOrder(Order order);

    void setOrderItems(int orderId, List<OrderItem> orderItems);

    List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    List<Order> findOrdersByCreateTimeBetweenAndUserId(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime, @Param("userId") Integer userId);

}
