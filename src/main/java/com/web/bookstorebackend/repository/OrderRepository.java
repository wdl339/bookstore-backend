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

    @Query("SELECT o FROM Order o JOIN o.items i WHERE o.userId = :userId AND i.book.title LIKE %:keyword% " +
            "And o.createAt BETWEEN :startTime AND :endTime ORDER BY o.createAt DESC")
    Page<Order> findAllByUserIdAndKeywordAndCreateAtBetween(@Param("userId") Integer userId,
                                                            @Param("keyword") String keyword,
                                                            @Param("startTime") Instant startTime,
                                                            @Param("endTime") Instant endTime,
                                                            Pageable pageable);
    @Query("SELECT o FROM Order o JOIN o.items i WHERE o.userId = :userId AND i.book.title LIKE %:keyword% " +
            "And o.createAt BETWEEN :startTime AND :endTime ORDER BY o.createAt DESC")
    List<Order> findAllByUserIdAndKeywordAndCreateAtBetween2(@Param("userId") Integer userId,
                                                             @Param("keyword") String keyword,
                                                             @Param("startTime") Instant startTime,
                                                             @Param("endTime") Instant endTime);

    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.book.title LIKE %:keyword% " +
            "And o.createAt BETWEEN :startTime AND :endTime ORDER BY o.createAt DESC")
    Page<Order> findAllByKeywordAndCreateAtBetween(@Param("keyword") String keyword,
                                                    @Param("startTime") Instant startTime,
                                                    @Param("endTime") Instant endTime,
                                                    Pageable pageable);

    @Query("SELECT o FROM Order o JOIN o.items i WHERE i.book.title LIKE %:keyword% " +
            "And o.createAt BETWEEN :startTime AND :endTime ORDER BY o.createAt DESC")
    List<Order> findAllByKeywordAndCreateAtBetween2(@Param("keyword") String keyword,
                                                    @Param("startTime") Instant startTime,
                                                    @Param("endTime") Instant endTime);

    List<Order> findAllByCreateAtBetween(Instant startTime, Instant endTime);

    List<Order> findAllByCreateAtBetweenAndUserId(Instant startTime, Instant endTime, Integer userId);
}
