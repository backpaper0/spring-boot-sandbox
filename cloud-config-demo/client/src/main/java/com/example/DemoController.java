package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private final DemoProperties properties;

	public DemoController(DemoProperties properties) {
		this.properties = properties;
	}

	@GetMapping("/demo")
	public Object demo() {
		return properties;
	}
}
