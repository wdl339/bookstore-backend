package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dto.AddOrderDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderDao orderDao;

    private final CartService cartService;

    public OrderService(OrderDao orderDao, CartService cartService) {
        this.orderDao = orderDao;
        this.cartService = cartService;
    }

    public List<Order> getAllOrders() {
        return orderDao.findAll();
    }

    public ResponseDto addOrder(AddOrderDto AddOrderDto) {
        List<OrderItem> orderItems = cartService.getItemsByItemIds(AddOrderDto.getItemIds());

        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("No items in the order");
        }

        Order order = new Order(AddOrderDto, orderItems);
        orderDao.addOrder(order);
        return new ResponseDto(true, "Order added successfully");
    }

}
