package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dto.EditBookDto;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;
import java.util.List;

@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;

    public GetBooksDto findAll(Pageable pageable) {

        Page<Book> bookPage = bookrepository.findAll(pageable);
        List<Book> bookList = bookPage.getContent();
        long total = bookPage.getTotalElements();

        return new GetBooksDto(total, bookList);
    }

    public GetBooksDto findAllActive(Pageable pageable) {

        Page<Book> bookPage = bookrepository.findAllByActiveTrue(pageable);
        List<Book> bookList = bookPage.getContent();
        long total = bookPage.getTotalElements();

        return new GetBooksDto(total, bookList);
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

    public void updateBook(Book book, EditBookDto editBookDto){
        book.setTitle(editBookDto.getTitle());
        book.setAuthor(editBookDto.getAuthor());
        book.setDescription(editBookDto.getDescription());
        book.setIsbn(editBookDto.getIsbn());
        book.setPrice(editBookDto.getPrice());
        book.setStock(editBookDto.getStock());
        bookrepository.save(book);
    }
}