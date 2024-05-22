package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class ChangePasswordDto {
    String  oldPassword;
    String  newPassword;
}
