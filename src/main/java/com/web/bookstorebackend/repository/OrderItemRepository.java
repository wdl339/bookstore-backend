package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findByIdIn(List<Integer> orderIds);
}
