package com.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private InMemoryApiTokenRepository repository;

	@PostMapping
	public Object get(@RequestParam String username) {
		String apiToken = repository.generateApiToken(username);
		return Map.of("apiToken", apiToken);
	}
}
