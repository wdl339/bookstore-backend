package com.web.bookstorebackend.dto;

import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import lombok.Data;

import java.util.List;

@Data
public class GetOrdersDto {
    private long total;
    private List<Order> orders;

    public GetOrdersDto(long total, List<Order> orders) {
        this.total = total;
        this.orders = orders;
    }
}
