package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;


    public GetBooksDto getAllBooks(String keyword, Pageable pageable) {
        if (Objects.equals(keyword, "")) {
            return bookDao.findAll(pageable);
        } else {
            return bookDao.findByTitleContaining(keyword, pageable);
        }
    }

    public GetBooksDto getAllActiveBooks(String keyword, Pageable pageable) {
        if (Objects.equals(keyword, "")) {
            return bookDao.findAllActive(pageable);
        } else {
            return bookDao.findActiveByTitleContaining(keyword, pageable);
        }
    }

    public GetBooksDto getAllActiveBooksRelatedToTag(String tag, Pageable pageable) {
        try {
            return bookDao.findActiveRelatedToTag(tag, pageable);
        } catch (Exception e) {
            System.out.println("Error in getAllActiveBooksRelatedToTag");
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Book getBookById(Integer id) {
        return bookDao.findById(id);
    }


    public ResponseDto createBook(CreateBookDto createBookDto) {
        Book book = new Book(createBookDto.getTitle(), createBookDto.getAuthor(), createBookDto.getDescription(),
                createBookDto.getIsbn(), createBookDto.getPrice(),0, createBookDto.getStock(), true);
        bookDao.createBook(book, createBookDto.getCover());
        return new ResponseDto(true, "Book added successfully");
    }

    public List<GetRankBookDto> getRankBooks(String startTime, String endTime, Integer topNumber) {
//        System.out.println("startTime: " + startTime);
//        System.out.println("endTime: " + endTime);
        Instant start = Objects.equals(startTime, "") ? Instant.EPOCH : Instant.parse(startTime + "Z");
        Instant end = Objects.equals(endTime, "") ? Instant.now() : Instant.parse(endTime + "Z");
        List<Order> orders = orderDao.findOrdersByCreateTimeBetween(start, end);
        Map<String, Integer> rankBooks = new HashMap<>();

        for (Order order : orders) {
            List<OrderItem> orderItems = order.getItems();
            for (OrderItem orderItem : orderItems) {
                String title = orderItem.getBook().getTitle();
                Integer number = orderItem.getNumber();
                if (rankBooks.containsKey(title)) {
                    rankBooks.put(title, rankBooks.get(title) + number);
                } else {
                    rankBooks.put(title, number);
                }
            }
        }

        List<GetRankBookDto> result = new ArrayList<>();
        // 返回销量前topNumber的书籍
        rankBooks.entrySet().stream()
                .sorted((o1, o2) -> o2.getValue().compareTo(o1.getValue()))
                .limit(topNumber)
                .forEach(entry -> result.add(new GetRankBookDto(entry.getKey(), entry.getValue())));
        return result;
    }

    public ResponseDto updateCover(Integer id, String cover) {
        bookDao.updateCover(id, cover);
        return new ResponseDto(true, "Cover updated successfully");
    }

    public ResponseDto updateBook(Integer id, EditBookDto editBookDto) {
        Book book = bookDao.findById(id);
        if (book == null) {
            return new ResponseDto(false, "Book not found");
        }

        bookDao.updateBook(book, editBookDto);
        return new ResponseDto(true, "Book updated successfully");
    }

    public ResponseDto changeBookHide(Integer id) {
        Book book = bookDao.findById(id);
        if (book == null) {
            return new ResponseDto(false, "Book not found");
        }

        bookDao.updateActive(book, !book.isActive());
        return new ResponseDto(true, "Book hide changed successfully");
    }
}