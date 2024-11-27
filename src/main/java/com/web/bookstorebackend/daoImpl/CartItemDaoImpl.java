package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.CartItemDao;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.BookCover;
import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.repository.BookCoverRepository;
import com.web.bookstorebackend.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class CartItemDaoImpl implements CartItemDao {

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private BookCoverRepository bookCoverRepository;

    public List<CartItem> findAllInCartByUserId(Integer userId) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserIdOrderByIdDesc(userId);

        return getCoverForCartItems(cartItems);
    }

    public List<CartItem> findAllInCartByUserIdAndKeyword(Integer userId, String keyword) {
        List<CartItem> cartItems = cartItemRepository.findAllByUserIdAndBookTitleContaining(userId, keyword);

        return getCoverForCartItems(cartItems);
    }

    private List<CartItem> getCoverForCartItems(List<CartItem> cartItems) {
        for(CartItem cartItem : cartItems) {
            Book book = cartItem.getBook();
            bookCoverRepository.findById(book.getId()).ifPresent(bookCover -> book.setCover(bookCover.getCoverBase64()));
            cartItem.setBook(book);
        }

        return cartItems;
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

    public void deleteItem(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }

    public void deleteItems(List<CartItem> cartItems) {
        cartItemRepository.deleteAll(cartItems);
    }
}
