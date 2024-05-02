package com.web.bookstorebackend.dto;

import com.web.bookstorebackend.model.Book;
import lombok.Data;

import java.util.List;

@Data
public class GetBooksDto {
    private Integer total;
    private List<Book> books;

    public GetBooksDto(List<Book> books) {
        this.total = books.size();
        this.books = books;
    }
}
