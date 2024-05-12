package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.util.OrderItemStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderDao orderDao;

    private final OrderItemDao orderItemDao;

    private final CartService cartService;

    public OrderService(OrderDao orderDao, OrderItemDao orderItemDao, CartService cartService) {
        this.orderDao = orderDao;
        this.orderItemDao = orderItemDao;
        this.cartService = cartService;
    }

    public List<Order> getOrders(int userId) {

        return orderDao.findAllOrdersByUserId(userId);
    }

    public ResponseDto addOrderFromCart(AddOrderFromCartDto addOrderFromCartDto, int userId) {
        List<Integer> orderIds = addOrderFromCartDto.getItemIds();
        List<OrderItem> orderItems = orderItemDao.findByItemIds(orderIds);

        if (orderItems.isEmpty()) {
            throw new IllegalArgumentException("No items in the order");
        }
        if (orderItems.size() != addOrderFromCartDto.getItemIds().size()) {
            throw new IllegalArgumentException("Some items are not in the order");
        }
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getUserId() != userId) {
                throw new IllegalArgumentException("Some items are not in user's cart");
            }
        }

        orderItemDao.updateOrderItemsStatus(orderItems);
        Order order = new Order(addOrderFromCartDto, orderItems, userId);
        orderDao.addOrder(order);
        return new ResponseDto(true, "Order added successfully");
    }

    public ResponseDto addOrderFromBook(int bookId, AddOrderFromBookDto addOrderFromBookDto, int userId) {
        AddToCartDto addToCartDto = new AddToCartDto(bookId, addOrderFromBookDto.getNumber());
        int itemId = cartService.addToCart(addToCartDto, userId);

        OrderItem orderItem = orderItemDao.findById(itemId);
        orderItemDao.updateOrderItemStatus(orderItem);
        Order order = new Order(addOrderFromBookDto, orderItem, userId);
        orderDao.addOrder(order);
        return new ResponseDto(true, "Order added successfully");
    }

}
