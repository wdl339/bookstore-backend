package com.web.bookstorebackend.dto;

import com.web.bookstorebackend.model.Book;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Data
@Getter
public class GetBooksDto {
    private long total;
    private List<Book> books;

    public GetBooksDto() {
    }

    public GetBooksDto(long total, List<Book> books) {
        this.total = total;
        this.books = books;
    }
}
