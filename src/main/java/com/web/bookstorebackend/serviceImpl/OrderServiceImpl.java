package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.CartService;
import com.web.bookstorebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderItemDao orderItemDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartService cartService;

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

        // 虽然加入购物车时会检查库存是否够，但仍然可能出现加入购物车后才出现库存不够的情况
        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            Book book = orderItem.getBook();
            int stock = book.getStock();
            int number = orderItem.getNumber();
            if (stock < number) {
                throw new IllegalArgumentException("Book " + book.getTitle() + " is out of stock");
            }
        }

        int totalPrice = orderItems.stream().mapToInt(item -> item.getBook().getPrice() * item.getNumber()).sum();
        User user = userDao.findUserById(userId);
        if (user.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Not enough balance");
        }

        for (int i = 0; i < orderItems.size(); i++) {
            OrderItem orderItem = orderItems.get(i);
            Book book = orderItem.getBook();
            int number = orderItem.getNumber();
            bookDao.updateStockAndSales(book, number);
        }

        userDao.updateBalance(user, totalPrice);
        orderItemDao.updateOrderItemsStatus(orderItems);
        Order order = new Order(addOrderFromCartDto, orderItems, userId, totalPrice);
        orderDao.addOrder(order);
        return new ResponseDto(true, "Order added successfully");
    }

    public ResponseDto addOrderFromBook(int bookId, AddOrderFromBookDto addOrderFromBookDto, int userId) {
        AddToCartDto addToCartDto = new AddToCartDto(bookId, addOrderFromBookDto.getNumber());
        int itemId = cartService.addToCart(addToCartDto, userId);
        OrderItem orderItem = orderItemDao.findById(itemId);

        // 虽然加入购物车时会检查库存是否够，但仍然可能出现加入购物车后才出现库存不够的情况
        Book book = orderItem.getBook();
        int stock = book.getStock();
        int number = orderItem.getNumber();
        if (stock < number) {
            throw new IllegalArgumentException("Book " + book.getTitle() + " is out of stock");
        }

        int totalPrice = orderItem.getBook().getPrice() * orderItem.getNumber();
        User user = userDao.findUserById(userId);
        if (user.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Not enough balance");
        }

        bookDao.updateStockAndSales(book, orderItem.getNumber());
        userDao.updateBalance(user, totalPrice);
        orderItemDao.updateOrderItemStatus(orderItem);
        Order order = new Order(addOrderFromBookDto, orderItem, userId, totalPrice);
        orderDao.addOrder(order);
        return new ResponseDto(true, "Order added successfully");
    }

}
