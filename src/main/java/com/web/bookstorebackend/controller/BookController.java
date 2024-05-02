package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.service.BookService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {

        this.bookService = bookService;
    }

    @GetMapping
    public GetBooksDto getAllBooks() {
        try {
            return bookService.getAllBooks();
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Integer id) {
        try {
            return bookService.getBookById(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseDto createBook(@RequestBody Book book) {
        try {
            return bookService.createBook(book);
        } catch (Exception e) {
            return null;
        }
    }

    
}
