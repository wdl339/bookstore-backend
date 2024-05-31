package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
import java.util.Base64;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<Object> getAllActiveBooks(@RequestParam String keyword,
                                                    @RequestParam int pageIndex,
                                                    @RequestParam int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            return ResponseEntity.ok(bookService.getAllActiveBooks(keyword, pageable));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllBooks(@RequestParam String keyword,
                                              @RequestParam int pageIndex,
                                              @RequestParam int pageSize) {
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            return ResponseEntity.ok(bookService.getAllBooks(keyword, pageable));
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

    @PostMapping("/{id}/cover")
    public ResponseEntity<Object> updateCover(@PathVariable Integer id,
                                              @RequestParam("cover") MultipartFile file) {
        try {
            String base64Avatar = Base64.getEncoder().encodeToString(file.getBytes());
            base64Avatar = "data:image/png;base64," + base64Avatar;

            return ResponseEntity.ok(bookService.updateCover(id, base64Avatar));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createBook(@RequestBody CreateBookDto createBookDto) {
        try {
            return ResponseEntity.ok(bookService.createBook(createBookDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<Object> updateBook(@PathVariable Integer id, @RequestBody EditBookDto editBookDto) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, editBookDto));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PutMapping("/{id}/hide")
    public ResponseEntity<Object> changeBookHide(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(bookService.changeBookHide(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }
    
}
