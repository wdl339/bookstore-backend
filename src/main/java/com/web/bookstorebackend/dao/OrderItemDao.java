package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.OrderItem;

import java.util.List;

public interface OrderItemDao {
    List<OrderItem> findAllInCartByUserId(Integer userId) ;

    List<OrderItem> findAllInCartByUserIdAndKeyword(Integer userId, String keyword) ;

    OrderItem findById(Integer id) ;

    List<OrderItem> findByItemIds(List<Integer> orderIds) ;
    void addOrderItem(OrderItem orderItem);

    void updateOrderItemNumber(OrderItem orderItem, Integer number) ;
    void deleteOrderItem(Integer id) ;
    void updateOrderItemStatus(OrderItem orderItem) ;

    void updateOrderItemsStatus(List<OrderItem> orderItems) ;


}
