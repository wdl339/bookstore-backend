package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.model.Book;

import java.util.List;

public interface BookDao {
    List<Book> findAll() ;

    List<Book> findByTitleContaining(String keyword);

    Book findById(Integer id) ;

    void save(Book book) ;

    void updateStockAndSales(Book book, Integer buyNumber) ;
}
