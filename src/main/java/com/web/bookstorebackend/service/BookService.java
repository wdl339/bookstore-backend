package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;

public interface BookService {

    GetBooksDto getAllBooks();

    Book getBookById(Integer id);


    ResponseDto createBook(Book book);
}