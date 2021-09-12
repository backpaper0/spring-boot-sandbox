package com.example;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/id")
public class IdController {

	private final RestTemplate http;

	public IdController(RestTemplate http) {
		this.http = http;
	}

	@GetMapping
	public Object get() {
		return http.getForObject("http://service2/id", Map.class);
	}
}
