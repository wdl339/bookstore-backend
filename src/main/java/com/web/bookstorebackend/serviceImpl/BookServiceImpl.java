package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;


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