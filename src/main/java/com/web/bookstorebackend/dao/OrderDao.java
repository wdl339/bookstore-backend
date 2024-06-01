package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.dto.GetOrdersDto;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;

public interface OrderDao {

    GetOrdersDto findOrdersByUserIdAndKeyword(@Param("userId") Integer userId, @Param("keyword") String keyword, Pageable pageable,
                                              @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    GetOrdersDto findOrdersByKeyword(@Param("keyword") String keyword, Pageable pageable,
                                     @Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    int addOrder(Order order);

    void setOrderItems(int orderId, List<OrderItem> orderItems);

    List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime);

    List<Order> findOrdersByCreateTimeBetweenAndUserId(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime,
                                                       @Param("userId") Integer userId);

}
