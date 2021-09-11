package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	private final Hello hello;

	public HelloController(Hello hello) {
		this.hello = hello;
	}

	@GetMapping
	public String get() {
		return hello.get();
	}
}
