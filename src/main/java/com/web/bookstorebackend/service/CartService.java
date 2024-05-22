package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.OrderItem;

import java.util.List;

public interface CartService {
    List<OrderItem> getCart(int userId);

    int addToCart(AddToCartDto addToCartDto, int userId) ;
    ResponseDto changeNumber(int id, int number, int userId) ;

    ResponseDto deleteItem(int id, int userId) ;

    void checkOrderItem(OrderItem orderItem, int userId);

    void checkBookAndNumber(Book book, int number);
}
