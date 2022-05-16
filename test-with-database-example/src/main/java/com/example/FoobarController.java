package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FoobarController {

	private final FoobarService service;

	public FoobarController(FoobarService service) {
		this.service = service;
	}

	@GetMapping("/foobar")
	public Object getAll() {
		return service.findAll();
	}
}
