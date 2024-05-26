package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import com.web.bookstorebackend.dto.GetBuyBookDto;
import com.web.bookstorebackend.dto.ResponseDto;
import com.web.bookstorebackend.model.Order;


import java.util.List;

public interface OrderService {
    List<Order> getOrders(int userId);

    ResponseDto addOrderFromCart(AddOrderFromCartDto addOrderFromCartDto, int userId);

    ResponseDto addOrderFromBook(int bookId, AddOrderFromBookDto addOrderFromBookDto, int userId);

    List<GetBuyBookDto> getBuyBooks(String startTime, String endTime, int userId);
}
