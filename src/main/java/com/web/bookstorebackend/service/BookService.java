package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookDao bookDao;

    public BookService(BookDao bookDao) {
        this.bookDao = bookDao;
    }

    public GetBooksDto getAllBooks() {
        return new GetBooksDto(bookDao.findAll());
    }

    public Book getBookById(Integer id) {
        return bookDao.findById(id);
    }


    public ResponseDto createBook(Book book) {
        bookDao.save(book);
        return new ResponseDto(true, "Book added successfully");
    }
}