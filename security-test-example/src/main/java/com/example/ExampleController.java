package com.example;

import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private final ExampleService exampleService;

	public ExampleController(ExampleService exampleService) {
		this.exampleService = Objects.requireNonNull(exampleService);
	}

	@GetMapping("/foo")
	public String getFoo(Authentication a) {
		return exampleService.get(a, "foo");
	}

	@GetMapping("/bar")
	public String getBar(Authentication a) {
		return exampleService.get(a, "bar");
	}

	@GetMapping("/baz")
	public String getBaz(Authentication a) {
		return exampleService.get(a, "baz");
	}

	@GetMapping("/qux")
	public String getQux(Authentication a) {
		return exampleService.get(a, "qux");
	}
}