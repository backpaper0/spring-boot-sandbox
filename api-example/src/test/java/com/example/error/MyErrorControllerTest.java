package com.example.error;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class MyErrorControllerTest {

	@Autowired
	TestRestTemplate http;

	@Test
	void notFound() {
		var response = http.getForEntity("/no-exists-path", Map.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertEquals(Map.of("message", "Not Found"), response.getBody());
	}

	@Test
	void serverError() {
		var response = http.getForEntity("/songs/simulate-bug", Map.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
		assertEquals(Map.of("message", "Internal Server Error"), response.getBody());
	}

	@Test
	void methodNotAllow() {
		var response = http.postForEntity("/songs/simulate-bug", Map.of("data", "dummy"), Map.class);
		assertEquals(HttpStatus.METHOD_NOT_ALLOWED, response.getStatusCode());
		assertEquals(Map.of("message", "Method Not Allowed"), response.getBody());
	}
}
