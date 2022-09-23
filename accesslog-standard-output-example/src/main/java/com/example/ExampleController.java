package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@GetMapping("/foo")
	public String getFoo() {
		return "foo";
	}

	@GetMapping("/bar")
	public String getBar() {
		return "bar";
	}
}
