package com.web.bookstorebackend.service;

import com.web.bookstorebackend.dto.*;
import com.web.bookstorebackend.model.Order;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface OrderService {
    GetOrdersDto getOrders(int userId, String keyword, Pageable pageable, String startTime, String endTime);

    GetOrdersDto getAllOrders(int userId, String keyword, Pageable pageable, String startTime, String endTime);

    ResponseDto addOrderFromCart(AddOrderFromCartDto addOrderFromCartDto, int userId);

    ResponseDto addOrderFromBook(int bookId, AddOrderFromBookDto addOrderFromBookDto, int userId);

    List<GetBuyBookDto> getBuyBooks(String startTime, String endTime, int userId);
}
