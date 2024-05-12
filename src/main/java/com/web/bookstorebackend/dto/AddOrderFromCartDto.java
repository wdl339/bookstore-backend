package com.web.bookstorebackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddOrderFromCartDto {

    private String address;
    private String receiver;
    private String phone;
    private List<Integer> itemIds;

}