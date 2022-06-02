package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

	@GetMapping
	public Object findAll() {
		var order = Map.of("orderId", 1);
		return Map.of("orders", List.of(order));
	}
}
