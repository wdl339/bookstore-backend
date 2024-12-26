package com.web.bookstorebackend.daoImpl;

import com.web.bookstorebackend.dao.OrderDao;
import com.web.bookstorebackend.dto.GetOrdersDto;
import com.web.bookstorebackend.model.Book;
import com.web.bookstorebackend.model.Order;
import com.web.bookstorebackend.model.OrderItem;
import com.web.bookstorebackend.repository.BookCoverRepository;
import com.web.bookstorebackend.repository.OrderRepository;
import org.springframework.transaction.annotation.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
//@Transactional("transactionManager")
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private BookCoverRepository bookCoverRepository;

    public GetOrdersDto findOrdersByUserIdAndKeyword(@Param("userId") Integer userId,
                                                     @Param("keyword") String keyword,
                                                     Pageable pageable,
                                                     @Param("startTime") Instant startTime,
                                                     @Param("endTime") Instant endTime) {
        Page<Order> orderPage = orderRepository.findAllByUserIdAndKeywordAndCreateAtBetween(userId, keyword, startTime, endTime, pageable);
        List<Order> orderList = orderPage.getContent();
        List<Order> orderPageAll = orderRepository.findAllByUserIdAndKeywordAndCreateAtBetween2(userId, keyword, startTime, endTime);
        List<Order> orderListWithCover = getCoverForOrderList(orderList);
        long total = orderPageAll.size();
        return new GetOrdersDto(total, orderListWithCover);
    }

    private List<Order> getCoverForOrderList(List<Order> orderList) {
        for (Order order : orderList) {
            List<OrderItem> items = order.getItems();
            for (OrderItem item : items) {
                Book book = item.getBook();
                bookCoverRepository.findById(book.getId()).ifPresent(bookCover -> book.setCover(bookCover.getCoverBase64()));
                item.setBook(book);
            }
        }
        return orderList;
    }

    public GetOrdersDto findOrdersByKeyword(@Param("keyword") String keyword,
                                            Pageable pageable,
                                            @Param("startTime") Instant startTime,
                                            @Param("endTime") Instant endTime) {
        Page<Order> orderPage = orderRepository.findAllByKeywordAndCreateAtBetween(keyword, startTime, endTime, pageable);
        List<Order> orderList = orderPage.getContent();
        List<Order> orderPageAll = orderRepository.findAllByKeywordAndCreateAtBetween2(keyword, startTime, endTime);
        List<Order> orderListWithCover = getCoverForOrderList(orderList);
        long total = orderPageAll.size();
        return new GetOrdersDto(total, orderListWithCover);
    }

    @Transactional("transactionManager")
    public int addOrder(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional("transactionManager")
    public void setOrderItems(int orderId, List<OrderItem> orderItems){
        Order order = orderRepository.findById(orderId).orElse(null);
        if(order == null){
            return;
        }
        order.setItems(orderItems);
        orderRepository.save(order);
    }

    public List<Order> findOrdersByCreateTimeBetween(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime){

        List<Order> orders = orderRepository.findAllByCreateAtBetween(startTime, endTime);
        return getCoverForOrderList(orders);
    }

    public List<Order> findOrdersByCreateTimeBetweenAndUserId(@Param("startTime") Instant startTime, @Param("endTime") Instant endTime, @Param("userId") Integer userId){
        List<Order> orders = orderRepository.findAllByCreateAtBetweenAndUserId(startTime, endTime, userId);
        return getCoverForOrderList(orders);
    }
}
