package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    Page<Book> findByTitleContaining(String keyword, Pageable pageable);

    Page<Book> findAllByActiveTrue(Pageable pageable);

    Page<Book> findByActiveTrueAndTitleContaining(String keyword, Pageable pageable);

    @Query("SELECT b FROM Book b WHERE b.active = true AND (b.tag IN :tags OR b.tag2 IN :tags)")
    Page<Book> findByActiveTrueAndTagIn(List<String> tags, Pageable pageable);

}
