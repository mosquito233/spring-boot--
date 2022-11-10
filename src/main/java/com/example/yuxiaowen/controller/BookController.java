package com.example.yuxiaowen.controller;

import com.example.yuxiaowen.dto.Book;
import com.example.yuxiaowen.mqconfig.RabbitConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author yuxiaowen
 * @Date 2022/11/7
 */
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public BookController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping
    public void defaultMessage() {
        Book book = new Book();
        book.setId("1");
        book.setName("一起来学Spring Boot");
        this.rabbitTemplate.convertAndSend(RabbitConfig.DEFAULT_BOOK_QUEUE, book);
        this.rabbitTemplate.convertAndSend(RabbitConfig.MANUAL_BOOK_QUEUE, book);
    }
}
