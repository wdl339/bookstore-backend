package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.UserAuth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAuthRepository extends JpaRepository<UserAuth, Integer> {

}
