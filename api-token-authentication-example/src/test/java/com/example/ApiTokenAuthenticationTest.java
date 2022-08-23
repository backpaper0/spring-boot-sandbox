package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "example.api-token=0000000000")
public class ApiTokenAuthenticationTest {

	@Autowired
	TestRestTemplate http;

	@Test
	void unauthorized() {
		var responseEntity = http.getForEntity("/demo", Map.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	void ok() {
		var token = "0000000000";
		var requestEntity = RequestEntity.get("/demo").header("Authorization", "Bearer " + token).build();
		var responseEntity = http.exchange(requestEntity, Map.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("test", responseEntity.getBody().get("name"));
	}

	@Test
	void mistakeApiToken() {
		var token = "mistake";
		var requestEntity = RequestEntity.get("/demo").header("Authorization", "Bearer " + token).build();
		var responseEntity = http.exchange(requestEntity, Map.class);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}
}
