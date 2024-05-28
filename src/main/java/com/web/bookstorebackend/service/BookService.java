package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.Book;

import java.util.List;

public interface BookService {

    GetBooksDto getAllBooks(String keyword);

    GetBooksDto getAllActiveBooks(String keyword);

    Book getBookById(Integer id);

    ResponseDto createBook(CreateBookDto createBookDto);

    List<GetRankBookDto> getRankBooks(String startTime, String endTime, Integer topNumber);

    ResponseDto updateCover(Integer id, String cover);

    ResponseDto updateBook(Integer id, EditBookDto editBookDto);

    ResponseDto changeBookHide(Integer id);
}