package com.web.bookstorebackend.serviceImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dao.OrderItemDao;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.GetRankBookDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookDao bookDao;

    @Autowired
    private OrderDao orderDao;


    public GetBooksDto getAllBooks() {
        return new GetBooksDto(bookDao.findAll());
    }

    public Book getBookById(Integer id) {
        return bookDao.findById(id);
    }


    public ResponseDto createBook(Book book) {
        bookDao.save(book);
        return new ResponseDto(true, "Book added successfully");
    }

    public List<GetRankBookDto> getRankBooks(String startTime, String endTime, Integer topNumber){
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
}