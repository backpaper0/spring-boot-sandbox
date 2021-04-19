package com.example;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

	private final MessageService service;

	public MessageController(MessageService service) {
		this.service = service;
	}

	@GetMapping
	public List<Message> findAll() {
		return service.findAll();
	}

	@PostMapping
	public Message create(@RequestParam String content) {
		return service.create(content);
	}
}
