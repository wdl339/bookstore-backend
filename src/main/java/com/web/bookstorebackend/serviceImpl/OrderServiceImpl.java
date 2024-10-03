package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.*;
import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.*;
import com.web.bookstorebackend.service.CartService;
import com.web.bookstorebackend.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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
    private CartItemDao cartItemDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CartService cartService;

    public GetOrdersDto getOrders(int userId, String keyword, Pageable pageable,
                                    String startTime, String endTime) {
        Instant start = Objects.equals(startTime, "") ? Instant.EPOCH : Instant.parse(startTime + "Z");
        Instant end = Objects.equals(endTime, "") ? Instant.now() : Instant.parse(endTime + "Z");
        return orderDao.findOrdersByUserIdAndKeyword(userId, keyword, pageable, start, end);
    }

    public GetOrdersDto getAllOrders(int userId, String keyword, Pageable pageable,
                                       String startTime, String endTime) {
        Instant start = Objects.equals(startTime, "") ? Instant.EPOCH : Instant.parse(startTime + "Z");
        Instant end = Objects.equals(endTime, "") ? Instant.now() : Instant.parse(endTime + "Z");
        return orderDao.findOrdersByKeyword(keyword, pageable, start, end);
    }

    @Transactional
    public String addOrderFromCart(AddOrderFromCartDto addOrderFromCartDto, int userId) {
        List<Integer> cartIds = addOrderFromCartDto.getItemIds();
        List<CartItem> cartItems = cartItemDao.findByItemIds(cartIds);

        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("No items in the cart");
        }
        if (cartItems.size() != addOrderFromCartDto.getItemIds().size()) {
            throw new IllegalArgumentException("Some items are not in the cart");
        }
        for (CartItem cartItem : cartItems) {
            if (cartItem.getUserId() != userId) {
                throw new IllegalArgumentException("Some items are not in user's cart");
            }
            if (!(cartItem.getBook().isActive())) {
                throw new IllegalArgumentException("Book " + cartItem.getBook().getTitle() + " is off the shelf");
            }
        }

        // 虽然加入购物车时会检查库存是否够，但仍然可能出现加入购物车后才出现库存不够的情况
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            Book book = cartItem.getBook();
            checkStock(cartItem, book);
        }

        int totalPrice = cartItems.stream().mapToInt(item -> item.getBook().getPrice() * item.getNumber()).sum();
        User user = userDao.findUserById(userId);
        checkBalance(user, totalPrice);

        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            Book book = cartItem.getBook();
            int number = cartItem.getNumber();
            bookDao.updateStockAndSales(book, number);
        }

        userDao.updateBalance(user, totalPrice);

        Order order = new Order(addOrderFromCartDto, userId, totalPrice);
        int orderId = orderDao.addOrder(order);

        List<OrderItem> orderItems = new ArrayList<>();
        for (int i = 0; i < cartItems.size(); i++) {
            CartItem cartItem = cartItems.get(i);
            OrderItem orderItem = new OrderItem(cartItem, orderId);
            orderItems.add(orderItem);
        }

        orderItemDao.addOrderItems(orderItems);
        orderDao.setOrderItems(orderId, orderItems);
        cartItemDao.deleteItems(cartItems);

        return "User " + userId + " adds order from cart successfully, order id: " + orderId;
    }

    @Transactional
    public String addOrderFromBook(int bookId, AddOrderFromBookDto addOrderFromBookDto, int userId) {
        AddToCartDto addToCartDto = new AddToCartDto(bookId, addOrderFromBookDto.getNumber());
        int itemId = cartService.addToCart(addToCartDto, userId);
        CartItem cartItem = cartItemDao.findById(itemId);

        Book book = cartItem.getBook();
        checkStock(cartItem, book);

        int totalPrice = cartItem.getBook().getPrice() * cartItem.getNumber();
        User user = userDao.findUserById(userId);
        checkBalance(user, totalPrice);

        bookDao.updateStockAndSales(book, cartItem.getNumber());
        userDao.updateBalance(user, totalPrice);

        Order order = new Order(addOrderFromBookDto, userId, totalPrice);
        int orderId = orderDao.addOrder(order);

        OrderItem orderItem = new OrderItem(cartItem, orderId);
        orderItemDao.addOrderItem(orderItem);
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        orderDao.setOrderItems(orderId, orderItems);

        cartItemDao.deleteItem(cartItem);
        return "User " + userId + " adds order from book successfully, order id: " + orderId;
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

    public void checkStock(CartItem cartItem, Book book) {
        int stock = book.getStock();
        int number = cartItem.getNumber();
        if (stock < number) {
            throw new IllegalArgumentException("Book " + book.getTitle() + " is out of stock");
        }
    }

    public void checkBalance(User user, int totalPrice) {
        if (user.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Not enough balance");
        }
    }
}
