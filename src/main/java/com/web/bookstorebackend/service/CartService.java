package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.util.OrderItemStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CartService {

    private final OrderItemDao orderItemDao;

    private final BookService bookService;

    public CartService(OrderItemDao orderItemDao, BookService bookService) {
        this.orderItemDao = orderItemDao;
        this.bookService = bookService;
    }

    public List<OrderItem> getCart(int userId) {

        return orderItemDao.findAllInCartByUserId(userId);
    }

    public int addToCart(AddToCartDto addToCartDto, int userId) {
        int bookId = addToCartDto.getBookId();
        Book book = bookService.getBookById(bookId);

        int buyNumber = addToCartDto.getNumber();
        checkBookAndNumber(book, buyNumber);

        List<OrderItem> orderItems = orderItemDao.findAllInCartByUserId(userId);
        for (OrderItem orderItem : orderItems) {
            if (orderItem.getBook().getId() == bookId) {
                throw new IllegalArgumentException("Book already in cart");
            }
        }

        OrderItem orderItem = new OrderItem(book, buyNumber, userId, OrderItemStatus.InCart);
        orderItemDao.addOrderItem(orderItem);
        return orderItem.getId();
    }

    public ResponseDto changeNumber(int id, int number, int userId) {
        OrderItem orderItem = orderItemDao.findById(id);
        Book book = orderItem.getBook();

        checkOrderItem(orderItem, userId);
        checkBookAndNumber(book, number);

        orderItemDao.updateOrderItemNumber(orderItem, number);
        return new ResponseDto(true, "Number changed successfully");
    }

    public ResponseDto deleteItem(int id, int userId) {
        OrderItem orderItem = orderItemDao.findById(id);

        checkOrderItem(orderItem, userId);

        orderItemDao.deleteOrderItem(id);
        return new ResponseDto(true, "Item deleted successfully");
    }

    public void checkOrderItem(OrderItem orderItem, int userId) {
        if (orderItem == null) {
            throw new NoSuchElementException("Item not found");
        }
        if (orderItem.getStatus() != OrderItemStatus.InCart) {
            throw new IllegalArgumentException("Item is not in cart");
        }
        if (orderItem.getUserId() != userId) {
            throw new IllegalArgumentException("Item does not belong to user");
        }
    }

    public void checkBookAndNumber(Book book, int number) {
        if (book == null) {
            throw new NoSuchElementException("Book not found");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("Number should be greater than 0");
        }
        if (book.getStock() < number) {
            throw new IllegalArgumentException("Not enough stock");
        }
    }

}
