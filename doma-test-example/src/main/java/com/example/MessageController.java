package com.example;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageDao dao;

    public MessageController(final MessageDao dao) {
        this.dao = dao;
    }

    @GetMapping
    public List<Message> selectAll() {
        return dao.selectAll();
    }
}
