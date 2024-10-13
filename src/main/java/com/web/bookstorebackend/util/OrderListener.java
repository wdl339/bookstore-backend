package com.web.bookstorebackend.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.bookstorebackend.dto.AddOrderFromCartDto;
import com.web.bookstorebackend.dto.AddOrderFromBookDto;
import com.web.bookstorebackend.service.OrderService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderListener {

    @Autowired
    private OrderService orderService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private WebSocketServer ws;

    @KafkaListener(topics = "order", groupId = "group_order")
    public void orderListener(ConsumerRecord<String, String> record) {
        String result;
        int userId = 0;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String message = record.value();
            String[] keys = record.key().split(":");
            userId = Integer.parseInt(keys[0]);
            if (message.startsWith("cart:")) {
                AddOrderFromCartDto addOrderFromCartDto = objectMapper.readValue(message.substring(5), AddOrderFromCartDto.class);
                result = orderService.addOrderFromCart(addOrderFromCartDto, userId);
            } else if (message.startsWith("book:")) {
                int bookId = Integer.parseInt(keys[1]);
                AddOrderFromBookDto addOrderFromBookDto = objectMapper.readValue(message.substring(5), AddOrderFromBookDto.class);
                result = orderService.addOrderFromBook(bookId, addOrderFromBookDto, userId);
            } else {
                result = "Failed to parse message";
            }
        } catch (Exception e) {
            result = "Failed because of exception: " + e.getMessage();
        }

        kafkaTemplate.send("order_finished", String.valueOf(userId), result);
    }

    @KafkaListener(topics = "order_finished", groupId = "group_order")
    public void orderFinishedListener(ConsumerRecord<String, String> record) throws InterruptedException {
        String message = record.value();
        System.out.println(message);
        ws.sendMessageToUser(record.key(), message);
    }

}

//zkServer.cmd
//cd D:\kafka\kafka_2.13-3.8.0
//.\bin\windows\kafka-server-start.bat .\config\server.properties