package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TypeConversionFunctionsTest {

	@LocalServerPort
	int port;

	RestTemplate http;

	@BeforeEach
	void setup() {
		http = new RestTemplate();
		http.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) {
				return false;
			}
		});
	}

	@Test
	void person() {
		var responseType = new ParameterizedTypeReference<Map<String, Object>>() {
		};
		var response = http.exchange(RequestEntity.post("http://localhost:" + port + "/person")
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
		var response = http.exchange(RequestEntity.post("http://localhost:" + port + "/person")
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
