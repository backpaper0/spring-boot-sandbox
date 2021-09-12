package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BuzzController {

	@GetMapping("/buzz")
	public String buzz() {
		return "Buzz";
	}
}
