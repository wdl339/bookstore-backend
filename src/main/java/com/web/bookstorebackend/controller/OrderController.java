package com.web.bookstorebackend.controller;

import com.web.bookstorebackend.dto.GetBooksDto;
import com.web.bookstorebackend.dto.AddOrderDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        try {
            return orderService.getAllOrders();
        } catch (Exception e) {
            return null;
        }
    }

    @PostMapping
    public ResponseDto addOrder(@RequestBody AddOrderDto AddOrderDto) {
        try {
            return orderService.addOrder(AddOrderDto);
        } catch (Exception e) {
            return new ResponseDto(false, e.getMessage());
        }
    }
}
