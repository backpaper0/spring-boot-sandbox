package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FizzController {

	@GetMapping("/fizz")
	public String fizz() {
		return "Fizz";
	}
}
