package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.repository.OrderRepository;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> findAllOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserIdOrderByCreateAtDesc(userId);
    }

    public void addOrder(Order order) {
        orderRepository.save(order);
    }

    public List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime){
        return orderRepository.findAllByCreateAtBetween(startTime, endTime);
    }

    public List<Order> findOrdersByCreateTimeBetweenAndUserId(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime, @Param("userId") Integer userId){
        return orderRepository.findAllByCreateAtBetweenAndUserId(startTime, endTime, userId);
    }
}
