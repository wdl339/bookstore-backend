package com.web.bookstorebackend.repository;

import com.web.bookstorebackend.model.CartItem;
import com.web.bookstorebackend.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

    List<CartItem> findAllByUserIdOrderByIdDesc(Integer userId);

    List<CartItem> findAllByUserIdAndBookTitleContaining(Integer userId, String keyword);

    List<CartItem> findByIdIn(List<Integer> cartIds);

}
