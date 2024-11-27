package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.BookDao;
import com.web.bookstorebackend.dto.EditBookDto;
import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.BookCover;
import com.web.bookstorebackend.model.BookTag;
import com.web.bookstorebackend.repository.BookCoverRepository;
import com.web.bookstorebackend.repository.BookRepository;
import com.web.bookstorebackend.repository.BookTagRepository;
import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class BookDaoImpl implements BookDao {

    @Autowired
    private BookRepository bookrepository;

    @Autowired
    private BookCoverRepository bookCoverRepository;

    @Autowired
    private BookTagRepository bookTagRepository;

    public GetBooksDto findAll(Pageable pageable) {
        Page<Book> bookPage = bookrepository.findAll(pageable);
        return getGetBooksDto(bookPage);
    }

    public GetBooksDto findAllActive(Pageable pageable) {
        Page<Book> bookPage = bookrepository.findAllByActiveTrue(pageable);
        return getGetBooksDto(bookPage);
    }

    private GetBooksDto getGetBooksDto(Page<Book> bookPage) {
        GetBooksDto getBooksDto;
        List<Book> bookList = bookPage.getContent();
        for (Book book : bookList) {
            bookCoverRepository.findById(book.getId()).ifPresent(bookCover -> book.setCover(bookCover.getCoverBase64()));
        }
        long total = bookPage.getTotalElements();
        getBooksDto = new GetBooksDto(total, bookList);

        return getBooksDto;
    }

    public GetBooksDto findByTitleContaining(String keyword, Pageable pageable) {
        Page<Book> bookPage = bookrepository.findByTitleContaining(keyword, pageable);
        return getGetBooksDto(bookPage);
    }

    public GetBooksDto findActiveByTitleContaining(String keyword, Pageable pageable) {
        Page<Book> bookPage = bookrepository.findByActiveTrueAndTitleContaining(keyword, pageable);
        return getGetBooksDto(bookPage);
    }

    public GetBooksDto findActiveRelatedToTag(String tag, Pageable pageable) {
        System.out.println("tag: " + tag);
        List<String> tags = bookTagRepository.findRelatedTags(tag);
        for (String t : tags) {
            System.out.println("t: " + t);
        }
        Page<Book> bookPage = bookrepository.findByActiveTrueAndTagIn(tags, pageable);
        return getGetBooksDto(bookPage);
    }

    public Book findById(Integer id) {
        Book book;

        book = bookrepository.findById(id).orElse(null);
        if (book != null) {
            bookCoverRepository.findById(book.getId()).ifPresent(bookCover -> book.setCover(bookCover.getCoverBase64()));
        }

        return book;
    }

    public void createBook(Book book, String cover) {
        Book savedBook = bookrepository.save(book);
        BookCover bookCover = new BookCover(savedBook.getId(), cover);
        bookCoverRepository.save(bookCover);
    }

    public void updateCover(Integer id, String cover) {
        BookCover bookCover = bookCoverRepository.findById(id).orElse(null);
        if (bookCover != null) {
            bookCover.setCoverBase64(cover);
        } else {
            bookCover = new BookCover(id, cover);
        }
        bookCoverRepository.save(bookCover);
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

    public void updateActive(Book book, Boolean active) {
        book.setActive(active);
        bookrepository.save(book);
    }
}