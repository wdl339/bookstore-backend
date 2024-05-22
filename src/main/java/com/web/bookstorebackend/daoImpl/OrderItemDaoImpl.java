package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.OrderItemRepository;
import com.web.bookstorebackend.util.OrderItemStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderItemDaoImpl implements OrderItemDao {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> findAllInCartByUserId(Integer userId) {

        return orderItemRepository.findAllByUserIdAndStatusOrderByIdDesc(userId, OrderItemStatus.InCart);
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