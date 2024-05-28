package com.web.bookstorebackend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String author;

    @Column(length = 1000)
    private String description;

    private String isbn;

    private int price;

    @Column(columnDefinition = "LONGTEXT")
    private String cover;

    private int sales;

    private int stock;

    private boolean active;

    public Book() {
    }

    public Book (String title, String author, String description, String isbn, int price, String cover, int sales, int stock, boolean active) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.isbn = isbn;
        this.price = price;
        this.cover = cover;
        this.sales = sales;
        this.stock = stock;
        this.active = active;
    }


}
