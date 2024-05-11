package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDao {

    @Autowired
    private BookRepository bookrepository;


    public List<Book> findAll() {

        return bookrepository.findAll();
    }

    public Book findById(Integer id) {

        return bookrepository.findById(id).orElse(null);
    }

    public void save(Book book) {

        bookrepository.save(book);
    }
}