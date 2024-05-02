package com.web.bookstorebackend.model;

import com.web.bookstorebackend.dto.AddOrderDto;
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

    private Instant createAt;

    private String receiver;

    private String address;

    private String phone;

    private double totalPrice;

    @OneToMany
    private List<OrderItem> items;

    public Order(AddOrderDto AddOrderDto, List<OrderItem> items) {
        this.createAt = Instant.now();
        this.receiver = AddOrderDto.getReceiver();
        this.address = AddOrderDto.getAddress();
        this.phone = AddOrderDto.getPhone();
        this.totalPrice = AddOrderDto.getTotalPrice();
        this.items = items;
    }

    public Order() {

    }
}
