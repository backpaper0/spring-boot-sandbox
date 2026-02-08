package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Message;
import com.example.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Autowired
    private MessageService service;

    @PostMapping
    public Message insert(@RequestParam String content) {
        return service.insert(content);
    }

    @PostMapping("/bulk")
    public List<Message> bulkInsert(@RequestParam List<String> contents) {
        return service.bulkInsert(contents);
    }

    @GetMapping
    public List<Message> selectAll() {
        return service.selectAll();
    }
}
