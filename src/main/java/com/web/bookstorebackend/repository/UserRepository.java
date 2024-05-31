package com.web.bookstorebackend.repository;


import com.web.bookstorebackend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByName(String username);

    Page<User> findAllByNameContaining(String keyword, Pageable pageable);

}

