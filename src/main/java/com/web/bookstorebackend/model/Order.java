package com.web.bookstorebackend.model;

import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import jakarta.persistence.*;
import lombok.Data;

import java.security.Timestamp;
import java.time.Instant;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int userId;

    private Instant createAt;

    private String receiver;

    private String address;

    private String phone;

    private int totalPrice;

    @OneToMany(cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<OrderItem> items;

    public Order(AddOrderFromCartDto addOrderFromCartDto, List<OrderItem> items, int userId, int totalPrice) {
        this.createAt = Instant.now();
        this.receiver = addOrderFromCartDto.getReceiver();
        this.address = addOrderFromCartDto.getAddress();
        this.phone = addOrderFromCartDto.getPhone();
        this.totalPrice = totalPrice;
        this.items = items;
        this.userId = userId;
    }

    public Order(AddOrderFromBookDto addOrderFromBookDto, OrderItem orderItem, int userId, int totalPrice) {
        this.createAt = Instant.now();
        this.receiver = addOrderFromBookDto.getReceiver();
        this.address = addOrderFromBookDto.getAddress();
        this.phone = addOrderFromBookDto.getPhone();
        this.totalPrice = totalPrice;
        this.items = List.of(orderItem);
        this.userId = userId;
    }

    public Order() {

    }
}
