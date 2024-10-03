package com.web.bookstorebackend.model;

import com.web.bookstorebackend.dao.CartItemDao;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "order_id")
    private int orderId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int price;

    private int number;


    public OrderItem(Book book, int number, int orderId) {
        this.orderId = orderId;
        this.book = book;
        this.number = number;
        this.price = book.getPrice();
    }

    public OrderItem(CartItem cartItem, int orderId) {
        this.orderId = orderId;
        this.book = cartItem.getBook();
        this.number = cartItem.getNumber();
        this.price = cartItem.getBook().getPrice();
    }

    public OrderItem() {

    }
}
