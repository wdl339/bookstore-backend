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

    private double price;

    private String cover;

    private int sales;

    private int stock;
}
