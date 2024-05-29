package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.OrderItem;

import java.util.List;

public interface OrderItemDao {

    void addOrderItem(OrderItem orderItem);

    void addOrderItems(List<OrderItem> orderItems) ;


}
