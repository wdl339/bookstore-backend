package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Bookrepository extends JpaRepository<Book, Integer> {
}
