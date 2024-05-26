package com.web.bookstorebackend.dto;

import lombok.Data;

@Data
public class GetRankBookDto {
    String title;
    Integer sales;

    public GetRankBookDto(String title, Integer sales) {
        this.title = title;
        this.sales = sales;
    }
}
