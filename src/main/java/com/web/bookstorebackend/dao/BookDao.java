package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.dto.EditBookDto;
import com.web.bookstorebackend.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll() ;

    List<Book> findAllActive() ;

    List<Book> findByTitleContaining(String keyword);

    List<Book> findActiveByTitleContaining(String keyword) ;

    Book findById(Integer id) ;

    void save(Book book) ;

    void updateStockAndSales(Book book, Integer buyNumber) ;

    void updateBook(Book book, EditBookDto editBookDto) ;
}
