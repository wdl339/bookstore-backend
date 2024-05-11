package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class RegisterDto {
    private String name;
    private String password;
    private String phone;
    private String email;
}
