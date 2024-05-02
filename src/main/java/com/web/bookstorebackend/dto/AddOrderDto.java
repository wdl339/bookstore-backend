package com.web.bookstorebackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddOrderDto {
    private String address;
    private String receiver;
    private String phone;
    private List<Integer> itemIds;
    private double totalPrice;

}