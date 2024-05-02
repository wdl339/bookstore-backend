package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.OrderItem;
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

    public List<OrderItem> getCart() {
        return orderItemDao.findAll();
    }

    public List<OrderItem> getItemsByItemIds(List<Integer> orderIds) {
        return orderItemDao.findByItemIds(orderIds);
    }

    public ResponseDto addToCart(AddToCartDto addToCartDto) {
        Book book = bookService.getBookById(addToCartDto.getBookId());

        if (book == null) {
            throw new NoSuchElementException("Book not found");
        }

        OrderItem orderItem = new OrderItem(book, addToCartDto.getNumber());
        orderItemDao.addOrderItem(orderItem);
        return new ResponseDto(true,"Item added to cart successfully");
    }

    public ResponseDto changeNumber(Integer id, Integer number) {
        OrderItem orderItem = orderItemDao.findById(id);
        orderItem.setNumber(number);
        orderItemDao.updateOrderItem(orderItem);
        return new ResponseDto(true, "Number changed successfully");
    }

    public ResponseDto deleteItem(Integer id) {
        orderItemDao.deleteOrderItem(id);
        return new ResponseDto(true, "Item deleted successfully");
    }

}
