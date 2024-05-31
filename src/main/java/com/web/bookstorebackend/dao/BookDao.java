package com.web.bookstorebackend.dao;

import com.web.bookstorebackend.dto.EditBookDto;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.model.Book;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookDao {
    GetBooksDto findAll(Pageable pageable) ;

    GetBooksDto findAllActive(Pageable pageable) ;

    GetBooksDto findByTitleContaining(String keyword, Pageable pageable) ;

    GetBooksDto findActiveByTitleContaining(String keyword, Pageable pageable) ;

    Book findById(Integer id) ;

    void save(Book book) ;

    void updateStockAndSales(Book book, Integer buyNumber) ;

    void updateBook(Book book, EditBookDto editBookDto) ;
}
