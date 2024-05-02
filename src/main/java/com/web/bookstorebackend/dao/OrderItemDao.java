package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.OrderItemRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemDao {

    private final OrderItemRepository orderItemRepository;

    public OrderItemDao(OrderItemRepository orderItemRepository) {

        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderItem> findAll() {

        return orderItemRepository.findAll();
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

    public void updateOrderItem(OrderItem orderItem) {

        orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Integer id) {

        orderItemRepository.deleteById(id);
    }

}
