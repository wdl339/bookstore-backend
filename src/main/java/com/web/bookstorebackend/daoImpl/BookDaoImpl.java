package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;

    public List<Book> findAll() {

        return bookrepository.findAll();
    }

    public Book findById(Integer id) {

        return bookrepository.findById(id).orElse(null);
    }

    public void save(Book book) {

        bookrepository.save(book);
    }

    public void updateStockAndSales(Book book, Integer buyNumber) {
        Integer stock = book.getStock();
        Integer sales = book.getSales();
        book.setStock(stock - buyNumber);
        book.setSales(sales + buyNumber);
        bookrepository.save(book);
    }
}