package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.CartItemDao;
import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.CartItemRepository;
import com.web.bookstorebackend.repository.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CartItemDaoImp implements CartItemDao {

    @Autowired
    private CartItemRepository cartItemRepository;

    public List<CartItem> findAllInCartByUserId(Integer userId) {

        return cartItemRepository.findAllByUserIdOrderByIdDesc(userId);
    }

    public List<CartItem> findAllInCartByUserIdAndKeyword(Integer userId, String keyword) {

        return cartItemRepository.findAllByUserIdAndBookTitleContaining(userId, keyword);
    }

    public void addCartItem(CartItem cartItem) {

        cartItemRepository.save(cartItem);
    }

    public CartItem findById(Integer id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    public List<CartItem> findByItemIds(List<Integer> cartIds) {

        return cartItemRepository.findByIdIn(cartIds);
    }

    public void updateCartItemNumber(CartItem cartItem, Integer number) {
        cartItem.setNumber(number);
        cartItemRepository.save(cartItem);
    }

    public void deleteCartItem(Integer id) {
        cartItemRepository.deleteById(id);
    }

    public void deleteCartItems(List<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }

    public void deleteItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void deleteItems(List<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }
}
