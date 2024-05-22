package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
}
