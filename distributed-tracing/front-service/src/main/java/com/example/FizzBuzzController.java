package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@ConfigurationProperties(prefix = "my")
public class FizzBuzzController {

	private final RestTemplate http;
	private String fizzUrl;
	private String buzzUrl;
	private String fizzbuzzUrl;

	public FizzBuzzController(RestTemplate http) {
		this.http = http;
	}

	@GetMapping("/{num}")
	public String fizzbuzz(@PathVariable int num) {
		if (num % 15 == 0) {
			return http.getForObject(fizzbuzzUrl, String.class);
		} else if (num % 3 == 0) {
			return http.getForObject(fizzUrl, String.class);
		} else if (num % 5 == 0) {
			return http.getForObject(buzzUrl, String.class);
		}
		return Integer.toString(num);
	}

	public void setFizzUrl(String fizzUrl) {
		this.fizzUrl = fizzUrl;
	}

	public void setBuzzUrl(String buzzUrl) {
		this.buzzUrl = buzzUrl;
	}

	public void setFizzbuzzUrl(String fizzbuzzUrl) {
		this.fizzbuzzUrl = fizzbuzzUrl;
	}
}
