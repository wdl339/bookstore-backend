package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class GetRankUserDto {
    String name;
    Integer number;

    public GetRankUserDto(String name, Integer number) {
        this.name = name;
        this.number = number;
    }
}
