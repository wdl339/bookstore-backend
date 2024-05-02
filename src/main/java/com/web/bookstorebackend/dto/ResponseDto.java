package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private Boolean ok;
    private String message;

    public ResponseDto(Boolean ok, String message) {
        this.ok = ok;
        this.message = message;
    }
}