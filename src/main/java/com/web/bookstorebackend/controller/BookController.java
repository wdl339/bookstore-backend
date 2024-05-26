package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.GetRankBookDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Object> getAllBooks() {
        try {
            return ResponseEntity.ok(bookService.getAllBooks());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getBookById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(bookService.getBookById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/rank")
    public ResponseEntity<Object> getRankBooks(@RequestParam String startTime,
                                             @RequestParam String endTime,
                                             @RequestParam Integer topNumber) {
        try {
            return ResponseEntity.ok(bookService.getRankBooks(startTime, endTime, topNumber));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    // 仅供测试用
    @PostMapping
    public ResponseEntity<Object> createBook(@RequestBody Book book) {
        try {
            return ResponseEntity.ok(bookService.createBook(book));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }
    
}
