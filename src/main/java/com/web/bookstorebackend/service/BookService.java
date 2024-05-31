package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.Book;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BookService {

    GetBooksDto getAllBooks(String keyword, Pageable pageable);

    GetBooksDto getAllActiveBooks(String keyword, Pageable pageable);

    Book getBookById(Integer id);

    ResponseDto createBook(CreateBookDto createBookDto);

    List<GetRankBookDto> getRankBooks(String startTime, String endTime, Integer topNumber);

    ResponseDto updateCover(Integer id, String cover);

    ResponseDto updateBook(Integer id, EditBookDto editBookDto);

    ResponseDto changeBookHide(Integer id);
}