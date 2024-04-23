package com.web.bookstorebackend.service;

import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.repository.Bookrepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final Bookrepository bookrepository;

    public BookService(Bookrepository bookrepository) {
        this.bookrepository = bookrepository;
    }

    public List<Book> findAllBooks() {
        return bookrepository.findAll();
    }
}
