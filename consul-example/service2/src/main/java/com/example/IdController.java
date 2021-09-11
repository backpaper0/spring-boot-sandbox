package com.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/id")
public class IdController {

	@Value("${myapp.id}")
	private String id;

	@GetMapping
	public Object get() {
		return Map.of("id", id);
	}
}
