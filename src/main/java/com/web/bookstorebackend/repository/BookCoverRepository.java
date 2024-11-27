package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.BookCover;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookCoverRepository extends MongoRepository<BookCover, Integer> {
}