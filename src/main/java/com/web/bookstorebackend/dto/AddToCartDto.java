package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class AddToCartDto {
    private Integer bookId;
    private Integer number;

    public AddToCartDto(int bookId, Integer number) {
        this.bookId = bookId;
        this.number = number;
    }
}
