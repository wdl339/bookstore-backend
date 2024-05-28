package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dao.UserDao;
import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.model.User;
import com.web.bookstorebackend.service.CartService;
import com.web.bookstorebackend.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

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

    public List<Order> getOrders(int userId, String keyword) {
        if (Objects.equals(keyword, "")) {
            return orderDao.findAllOrdersByUserId(userId);
        } else {
            return orderDao.findOrdersByUserIdAndKeyword(userId, keyword);
        }
    }

    public List<Order> getAllOrders(int userId, String keyword) {
        if (Objects.equals(keyword, "")) {
            return orderDao.findAllOrders();
        } else {
            return orderDao.findOrdersByKeyword(keyword);
        }
    }

    @Transactional
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
            System.out.println(orderItem.getBook().isActive());
            if (!(orderItem.getBook().isActive())) {
                throw new IllegalArgumentException("Book " + orderItem.getBook().getTitle() + " is off the shelf");
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

    @Transactional
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

    public List<GetBuyBookDto> getBuyBooks(String startTime, String endTime, int userId) {
        Instant start = Objects.equals(startTime, "") ? Instant.EPOCH : Instant.parse(startTime + "Z");
        Instant end = Objects.equals(endTime, "") ? Instant.now() : Instant.parse(endTime + "Z");
        List<Order> orders = orderDao.findOrdersByCreateTimeBetweenAndUserId(start, end, userId);
        Map<Book, Integer> BookNum = new HashMap<>();
        Map<Book, Integer> BookPrice = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = order.getItems();
            for (OrderItem orderItem : orderItems) {
                Book book = orderItem.getBook();
                Integer number = orderItem.getNumber();
                Integer price = orderItem.getPrice();
                if (BookNum.containsKey(book)) {
                    BookNum.put(book, BookNum.get(book) + number);
                    BookPrice.put(book, BookPrice.get(book) + price * number);
                } else {
                    BookNum.put(book, number);
                    BookPrice.put(book, price * number);
                }
            }
        }

        List<GetBuyBookDto> result = new ArrayList<>();
        for (Map.Entry<Book, Integer> entry : BookNum.entrySet()) {
            Book book = entry.getKey();
            Integer number = entry.getValue();
            Integer price = BookPrice.get(book);
            result.add(new GetBuyBookDto(book, price, number));
        }
        return result;
    }
}
