package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.OrderItemRepository;
import com.web.bookstorebackend.util.OrderItemStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemDao {

    private final OrderItemRepository orderItemRepository;

    public OrderItemDao(OrderItemRepository orderItemRepository) {

        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findAllInCartByUserId(Integer userId) {

        return orderItemRepository.findAllByUserIdAndStatus(userId, OrderItemStatus.InCart);
    }

    public OrderItem findById(Integer id) {

        return orderItemRepository.findById(id).orElse(null);
    }

    public List<OrderItem> findByItemIds(List<Integer> orderIds) {

        return orderItemRepository.findByIdIn(orderIds);
    }

    public void addOrderItem(OrderItem orderItem) {

        orderItemRepository.save(orderItem);
    }

    public void updateOrderItemNumber(OrderItem orderItem, Integer number) {
        orderItem.setNumber(number);
        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Integer id) {

        orderItemRepository.deleteById(id);
    }

    public void updateOrderItemStatus(OrderItem orderItem) {
        orderItem.setStatus(OrderItemStatus.Ordered);
        orderItemRepository.save(orderItem);
    }

    public void updateOrderItemsStatus(List<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            orderItem.setStatus(OrderItemStatus.Ordered);
            orderItemRepository.save(orderItem);
        }
    }

}
