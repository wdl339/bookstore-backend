package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.OrderItemRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public void addOrderItem(OrderItem orderItem) {

        orderItemRepository.save(orderItem);
    }

    @Transactional
    public void addOrderItems(List<OrderItem> orderItems) {
        orderItemRepository.saveAll(orderItems);
    }

}