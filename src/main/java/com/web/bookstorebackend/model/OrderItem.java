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
    private Book book;

    private int number;

    private OrderItemStatus status;


    public OrderItem(Book book, int number, int userId, OrderItemStatus status) {
        this.book = book;
        this.number = number;
        this.userId = userId;
        this.status = status;
    }

    public OrderItem() {

    }
}
