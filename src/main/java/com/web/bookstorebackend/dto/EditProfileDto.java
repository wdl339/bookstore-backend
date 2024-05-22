package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class EditProfileDto {
    private String name;
    private String email;
    private String phone;
    private String address;
    private String description;
}
