package com.web.bookstorebackend.daoImpl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dto.EditBookDto;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Objects;

@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public GetBooksDto findAll(Pageable pageable) {

        GetBooksDto getBooksDto;
        String p = null;
        String totalStr = null;
        boolean redis_error = false;

        try {
            p = redisTemplate.opsForValue().get("books_in_page" + pageable.getPageNumber());
            totalStr = redisTemplate.opsForValue().get("books_total");
        } catch (Exception e) {
            System.out.println("redis error");
            redis_error = true;
        }

        if (p == null || totalStr == null){
            Page<Book> bookPage = bookrepository.findAll(pageable);
            List<Book> bookList = bookPage.getContent();
            long total = bookPage.getTotalElements();
            getBooksDto = new GetBooksDto(total, bookList);
            if (!redis_error){
                redisTemplate.opsForValue().set("books_in_page" + pageable.getPageNumber(), JSON.toJSONString(bookList));
                redisTemplate.opsForValue().set("books_total", String.valueOf(total));
            }
            System.out.println("get books from database");
        } else {
            List<Book> books = JSONObject.parseArray(p, Book.class);
            getBooksDto = new GetBooksDto(Long.parseLong(totalStr), books);
            System.out.println("get books from redis");
        }

        return getBooksDto;
    }

    public GetBooksDto findAllActive(Pageable pageable) {

        GetBooksDto getBooksDto;
        String p = null;
        String totalStr = null;
        boolean redis_error = false;

        try {
            p = redisTemplate.opsForValue().get("activeBooks_in_page" + pageable.getPageNumber());
            totalStr = redisTemplate.opsForValue().get("activeBooks_total");
        } catch (Exception e) {
            System.out.println("redis error");
            redis_error = true;
        }

        if (p == null || totalStr == null){
            Page<Book> bookPage = bookrepository.findAllByActiveTrue(pageable);
            List<Book> bookList = bookPage.getContent();
            long total = bookPage.getTotalElements();
            getBooksDto = new GetBooksDto(total, bookList);
            if (!redis_error){
                redisTemplate.opsForValue().set("activeBooks_in_page" + pageable.getPageNumber(), JSON.toJSONString(bookList));
                redisTemplate.opsForValue().set("activeBooks_total", String.valueOf(total));
            }
            System.out.println("get active books from database");
        } else {
            List<Book> books = JSONObject.parseArray(p, Book.class);
            getBooksDto = new GetBooksDto(Long.parseLong(totalStr), books);
            System.out.println("get active books from redis");
        }

        return getBooksDto;
    }

    public Book findById(Integer id) {
        Book book;
        String p = null;
        boolean redis_error = false;

        try {
            p = redisTemplate.opsForValue().get("book" + id);
        } catch (Exception e) {
            System.out.println("redis error");
            redis_error = true;
        }

        if (p == null){
            book = bookrepository.findById(id).orElse(null);
            if (book != null) {
                if (!redis_error)
                    redisTemplate.opsForValue().set("book" + id, JSON.toJSONString(book));
                System.out.println("get book " + id + " from database");
            }
        } else {
            book = JSON.parseObject(p, Book.class);
            System.out.println("get book " + id + " from redis");
        }

        return book;
    }

    public void save(Book book) {
        String p = null;

        try {
            p = redisTemplate.opsForValue().get("book" + book.getId());
        } catch (Exception e) {
            System.out.println("redis error");
        }

        if (p != null){
            Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
            redisTemplate.opsForValue().set("book" + book.getId(), JSON.toJSONString(book));
        }
        bookrepository.save(book);
    }

    public void updateStockAndSales(Book book, Integer buyNumber) {
        Integer stock = book.getStock();
        Integer sales = book.getSales();
        book.setStock(stock - buyNumber);
        book.setSales(sales + buyNumber);
        String p = null;

        try {
            p = redisTemplate.opsForValue().get("book" + book.getId());
        } catch (Exception e) {
            System.out.println("redis error");
        }

        if (p != null){
            Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
            redisTemplate.opsForValue().set("book" + book.getId(), JSON.toJSONString(book));
        }
        bookrepository.save(book);
    }

    public void updateBook(Book book, EditBookDto editBookDto){
        book.setTitle(editBookDto.getTitle());
        book.setAuthor(editBookDto.getAuthor());
        book.setDescription(editBookDto.getDescription());
        book.setIsbn(editBookDto.getIsbn());
        book.setPrice(editBookDto.getPrice());
        book.setStock(editBookDto.getStock());
        String p = null;

        try {
            p = redisTemplate.opsForValue().get("book" + book.getId());
        } catch (Exception e) {
            System.out.println("redis error");
        }

        if (p != null){
            Objects.requireNonNull(redisTemplate.getConnectionFactory()).getConnection().flushDb();
            redisTemplate.opsForValue().set("book" + book.getId(), JSON.toJSONString(book));
        }
        bookrepository.save(book);
    }

    public GetBooksDto findByTitleContaining(String keyword, Pageable pageable) {
        Page<Book> bookPage = bookrepository.findByTitleContaining(keyword, pageable);
        List<Book> bookList = bookPage.getContent();
        long total = bookPage.getTotalElements();
        return new GetBooksDto(total, bookList);
    }

    public GetBooksDto findActiveByTitleContaining(String keyword, Pageable pageable) {
        Page<Book> bookPage = bookrepository.findByActiveTrueAndTitleContaining(keyword, pageable);
        List<Book> bookList = bookPage.getContent();
        long total = bookPage.getTotalElements();
        return new GetBooksDto(total, bookList);
    }
}