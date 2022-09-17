package com.example.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/baz")
public class BazController {

	@GetMapping("/{id}")
	public Object get(
			@PathVariable Integer id) {
		return Map.of("id", id);
	}
}
