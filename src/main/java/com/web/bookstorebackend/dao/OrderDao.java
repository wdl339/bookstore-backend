package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Order;

import java.util.List;

public interface OrderDao {

    List<Order> findAllOrdersByUserId(Integer userId);

    void addOrder(Order order);
}
