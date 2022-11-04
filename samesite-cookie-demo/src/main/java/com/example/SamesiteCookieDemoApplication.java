package com.example;

import jakarta.servlet.http.HttpSession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SamesiteCookieDemoApplication {

	public static void main(final String[] args) {
		SpringApplication.run(SamesiteCookieDemoApplication.class, args);
	}

	@GetMapping("/")
	String index(final HttpSession session) {
		return "demo";
	}
}
