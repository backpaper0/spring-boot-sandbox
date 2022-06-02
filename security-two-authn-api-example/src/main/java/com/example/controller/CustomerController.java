package com.example.controller;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@GetMapping
	public Object findAll(Authentication authentication) {
		var customer = Map.of("customerName", authentication.getName());
		return Map.of("customers", List.of(customer));
	}
}
