package com.web.bookstorebackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private int userId;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    private int number;

    public CartItem() {

    }

    public CartItem(Book book, int number, int userId) {
        this.userId = userId;
        this.book = book;
        this.number = number;
    }

}
