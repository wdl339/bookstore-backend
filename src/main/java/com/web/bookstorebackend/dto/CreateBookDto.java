package com.web.bookstorebackend.dto;

import lombok.Data;
@Data
public class CreateBookDto {
    private String title;
    private String author;
    private String description;
    private String isbn;
    private int price;
    private int stock;
    private String cover;
}
