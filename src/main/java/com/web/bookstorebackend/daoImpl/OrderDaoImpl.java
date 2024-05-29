package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.OrderRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAllOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserIdOrderByCreateAtDesc(userId);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAllByOrderByCreateAtDesc();
    }

    public List<Order> findOrdersByUserIdAndKeyword(@Param("userId") Integer userId, @Param("keyword") String keyword) {
        return orderRepository.findAllByUserIdAndKeyword(userId, keyword);
    }

    public List<Order> findOrdersByKeyword(@Param("keyword") String keyword) {
        return orderRepository.findAllByKeyword(keyword);
    }

    public int addOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    public void setOrderItems(int orderId, List<OrderItem> orderItems){
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            return;
        }
        order.setItems(orderItems);
        orderRepository.save(order);
    }

    public List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime){
        return orderRepository.findAllByCreateAtBetween(startTime, endTime);
    }

    public List<Order> findOrdersByCreateTimeBetweenAndUserId(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime, @Param("userId") Integer userId){
        return orderRepository.findAllByCreateAtBetweenAndUserId(startTime, endTime, userId);
    }
}
