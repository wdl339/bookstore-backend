package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByUserIdOrderByCreateAtDesc(Integer userId);

    List<Order> findAllByCreateAtBetween(Instant startTime, Instant endTime);
}
