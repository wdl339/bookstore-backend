package com.web.bookstorebackend.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddOrderFromBookDto {
    private String address;
    private String receiver;
    private String phone;
    private Integer number;
}
