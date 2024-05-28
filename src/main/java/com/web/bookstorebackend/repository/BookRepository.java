package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findByTitleContaining(String keyword);

    List<Book> findAllByActiveTrue();
    List<Book> findByActiveTrueAndTitleContaining(String keyword);

}
