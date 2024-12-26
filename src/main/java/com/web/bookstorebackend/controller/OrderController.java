package com.web.bookstorebackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.service.OrderService;
import com.web.bookstorebackend.util.WebSocketServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private WebSocketServer ws;

    @GetMapping
    public ResponseEntity<Object> getOrders(@RequestParam String keyword,
                                 @RequestAttribute("userId") Integer userId,
                                 @RequestParam int pageIndex,
                                 @RequestParam int pageSize,
                                 @RequestParam String startTime,
                                 @RequestParam String endTime) {
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            return ResponseEntity.ok(orderService.getOrders(userId, keyword, pageable, startTime, endTime));
        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrders(@RequestParam String keyword,
                                 @RequestAttribute("userId") Integer userId,
                                    @RequestParam int pageIndex,
                                    @RequestParam int pageSize,
                                    @RequestParam String startTime,
                                    @RequestParam String endTime) {
        try {
            Pageable pageable = PageRequest.of(pageIndex, pageSize);
            return ResponseEntity.ok(orderService.getAllOrders(userId, keyword, pageable, startTime, endTime));
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseEntity<Object> addOrderFromCart(@RequestBody AddOrderFromCartDto addOrderFromCartDto,
                                           @RequestAttribute("userId") Integer userId) throws InterruptedException {
        try {
            orderService.addOrderFromCart(addOrderFromCartDto, userId);
            return ResponseEntity.ok(new ResponseDto(true, "Order success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @PostMapping("/{bookId}")
    public ResponseEntity<Object> addOrderFromBook(@PathVariable Integer bookId,
                                           @RequestBody AddOrderFromBookDto addOrderFromBookDto,
                                           @RequestAttribute("userId") Integer userId) {
        try {
            orderService.addOrderFromBook(bookId, addOrderFromBookDto, userId);
            return ResponseEntity.ok(new ResponseDto(true, "Order success"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getBuyBooks(@RequestParam String startTime,
                                              @RequestParam String endTime,
                                              @RequestAttribute("userId") Integer userId) {
        try {
            return ResponseEntity.ok(orderService.getBuyBooks(startTime, endTime, userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseDto(false, e.getMessage()));
        }
    }
}
