package com.web.bookstorebackend.dto;

import com.web.bookstorebackend.model.Book;
import lombok.Data;

@Data
public class GetBuyBookDto {
    Book book;
    int price;
    int number;

    public GetBuyBookDto(Book book, int price, int number) {
        this.book = book;
        this.price = price;
        this.number = number;
    }
}
