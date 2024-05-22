package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;


    @GetMapping
    public List<Order> getOrders(@RequestAttribute("userId") Integer userId) {
        try {
            return orderService.getOrders(userId);
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<Object> addOrderFromCart(@RequestBody AddOrderFromCartDto addOrderFromCartDto,
                                           @RequestAttribute("userId") Integer userId) {
        try {
            return ResponseEntity.ok(orderService.addOrderFromCart(addOrderFromCartDto, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<Object> addOrderFromBook(@PathVariable Integer bookId,
                                           @RequestBody AddOrderFromBookDto addOrderFromBookDto,
                                           @RequestAttribute("userId") Integer userId) {
        try {
            return ResponseEntity.ok(orderService.addOrderFromBook(bookId, addOrderFromBookDto, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }
}
