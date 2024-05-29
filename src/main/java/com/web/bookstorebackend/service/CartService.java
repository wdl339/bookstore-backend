package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.model.OrderItem;

import java.util.List;

public interface CartService {
    List<CartItem> getCart(int userId, String keyword) ;

    int addToCart(AddToCartDto addToCartDto, int userId) ;
    ResponseDto changeNumber(int id, int number, int userId) ;

    ResponseDto deleteItem(int id, int userId) ;

    void checkCartItem(CartItem cartItem, int userId) ;

    void checkBookAndNumber(Book book, int number);
}
