package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.model.OrderItem;

import java.util.List;

public interface CartItemDao {

    List<CartItem> findAllInCartByUserId(Integer userId) ;

    List<CartItem> findAllInCartByUserIdAndKeyword(Integer userId, String keyword) ;

    void addCartItem(CartItem cartItem) ;

    CartItem findById(Integer id) ;

    List<CartItem> findByItemIds(List<Integer> cartIds) ;

    void updateCartItemNumber(CartItem cartItem, Integer number) ;

    void deleteCartItem(Integer id) ;

    void deleteItem(CartItem cartItem) ;

    void deleteItems(List<CartItem> cartItems) ;
}
