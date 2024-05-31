package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    Page<Order> findAllByUserIdOrderByCreateAtDesc(Integer userId, Pageable pageable);

    Page<Order> findAllByOrderByCreateAtDesc(Pageable pageable);
    @Query("SELECT o FROM Order o JOIN o.items i WHERE o.userId = :userId AND i.book.title LIKE %:keyword% ORDER BY o.createAt DESC")
    Page<Order> findAllByUserIdAndKeyword(@Param("userId") Integer userId, @Param("keyword") String keyword, Pageable pageable);

    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.book.title LIKE %:keyword% ORDER BY o.createAt DESC")
    Page<Order> findAllByKeyword(@Param("keyword") String keyword, Pageable pageable);

    List<Order> findAllByCreateAtBetween(Instant startTime, Instant endTime);

    List<Order> findAllByCreateAtBetweenAndUserId(Instant startTime, Instant endTime, Integer userId);
}
