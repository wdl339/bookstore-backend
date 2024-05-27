package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.GetRankBookDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;

import java.util.List;

public interface BookService {

    GetBooksDto getAllBooks(String keyword);

    Book getBookById(Integer id);

    ResponseDto createBook(Book book);

    List<GetRankBookDto> getRankBooks(String startTime, String endTime, Integer topNumber);
}