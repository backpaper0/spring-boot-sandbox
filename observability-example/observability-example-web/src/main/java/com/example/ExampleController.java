package com.example;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.observation.annotation.Observed;

@RestController
@RequestMapping("/")
@Observed
public class ExampleController {

	private final ExampleService service;

	public ExampleController(ExampleService service) {
		this.service = service;
	}

	@GetMapping
	public Object get() {
		String message = service.sayHello("world");
		return Map.of("message", message);
	}
}
