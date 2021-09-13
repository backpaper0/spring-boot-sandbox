package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TypeConversionFunctionsTest {

	@Autowired
	private TestRestTemplate http;

	@Test
	void person() {
		var responseType = new ParameterizedTypeReference<Map<String, Object>>() {
		};
		var response = http.exchange(RequestEntity.post("/person")
				.body(Map.of("name", "Alice")), responseType);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		var responseBody = response.getBody();
		assertNotNull(responseBody.get("id"));
		UUID.fromString(responseBody.get("id").toString());
		assertEquals("Alice", responseBody.get("name"));
	}

	@Test
	void persons() {
		var responseType = new ParameterizedTypeReference<List<Map<String, Object>>>() {
		};
		var response = http.exchange(RequestEntity.post("/person")
				.body(List.of(Map.of("name", "Alice"), Map.of("name", "Bob"))), responseType);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		var responseBody = response.getBody();

		var iterator = responseBody.iterator();

		var person = iterator.next();
		assertNotNull(person.get("id"));
		UUID.fromString(person.get("id").toString());
		assertEquals("Alice", person.get("name"));

		person = iterator.next();
		assertNotNull(person.get("id"));
		UUID.fromString(person.get("id").toString());
		assertEquals("Bob", person.get("name"));

		assertFalse(iterator.hasNext());
	}
}
