package com.web.bookstorebackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private Book book;

    private int number;

    public OrderItem(Book book, int number) {
        this.book = book;
        this.number = number;
    }

    public OrderItem() {

    }
}
