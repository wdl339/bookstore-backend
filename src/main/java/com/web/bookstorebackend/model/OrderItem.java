package com.web.bookstorebackend.model;

import com.web.bookstorebackend.util.OrderItemStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int price;

    private int number;

    private OrderItemStatus status;


    public OrderItem(Book book, int number, int userId, OrderItemStatus status) {
        this.userId = userId;
        this.book = book;
        this.number = number;
        this.price = book.getPrice();
        this.status = status;
    }

    public OrderItem() {

    }
}
