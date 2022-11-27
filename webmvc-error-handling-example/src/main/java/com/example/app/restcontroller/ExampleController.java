package com.example.app.restcontroller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.app.exception.HandlingException;

@RestController
@RequestMapping("api")
public class ExampleController {

	@GetMapping("a")
	public Object get() {
		return Map.of("message", "Hello World");
	}

	@GetMapping("b")
	public Object handlingException() {
		throw new HandlingException();
	}

	@GetMapping("c")
	public Object unhandlingException() {
		throw new RuntimeException();
	}
}
