package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.repository.OrderRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDao {

    private final OrderRepository orderRepository;

    public OrderDao(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findAllOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }
}
