package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;

@SpringBootApplication
@RestController
public class SamesiteCookieDemoApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(SamesiteCookieDemoApplication.class, args);
	}

	@GetMapping("/")
	String index(final HttpSession session) {
		return "demo";
	}
}
