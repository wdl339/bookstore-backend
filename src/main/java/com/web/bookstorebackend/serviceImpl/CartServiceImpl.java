package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.CartItemDao;
import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dto.AddToCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.service.BookService;
import com.web.bookstorebackend.service.CartService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
//@Transactional("transactionManager")
public class CartServiceImpl implements CartService {

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private BookService bookService;

    public List<CartItem> getCart(int userId, String keyword) {
        if (Objects.equals(keyword, ""))  {
            return cartItemDao.findAllInCartByUserId(userId);
        } else {
            return cartItemDao.findAllInCartByUserIdAndKeyword(userId, keyword);
        }
    }

    @Transactional("transactionManager")
    public int addToCart(AddToCartDto addToCartDto, int userId) {
        int bookId = addToCartDto.getBookId();
        Book book = bookService.getBookById(bookId);

        int buyNumber = addToCartDto.getNumber();
        checkBookAndNumber(book, buyNumber);

        List<CartItem> cartItems = cartItemDao.findAllInCartByUserId(userId);
        for (CartItem cartItem : cartItems) {
            if (cartItem.getBook().getId() == bookId) {
                throw new IllegalArgumentException("Book already in cart");
            }
        }

        CartItem cartItem = new CartItem(book, buyNumber, userId);
        cartItemDao.addCartItem(cartItem);
        return cartItem.getId();
    }

    @Transactional("transactionManager")
    public ResponseDto changeNumber(int id, int number, int userId) {
        CartItem cartItem = cartItemDao.findById(id);
        Book book = cartItem.getBook();

        checkCartItem(cartItem, userId);
        checkBookAndNumber(book, number);

        cartItemDao.updateCartItemNumber(cartItem, number);
        return new ResponseDto(true, "Number changed successfully");
    }

    @Transactional("transactionManager")
    public ResponseDto deleteItem(int id, int userId) {
        CartItem cartItem = cartItemDao.findById(id);

        checkCartItem(cartItem, userId);

        cartItemDao.deleteCartItem(id);
        return new ResponseDto(true, "Item deleted successfully");
    }

    public void checkCartItem(CartItem cartItem, int userId) {
        if (cartItem == null) {
            throw new NoSuchElementException("Item not found");
        }
        if (cartItem.getUserId() != userId) {
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
