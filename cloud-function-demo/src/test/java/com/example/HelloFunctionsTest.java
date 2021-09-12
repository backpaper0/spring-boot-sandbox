package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloFunctionsTest {

	@Autowired
	private TestRestTemplate http;

	@Test
	void helloSupplier() {
		ResponseEntity<String> response = http.getForEntity("/helloSupplier", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, world!", response.getBody());
	}

	@Test
	void helloConsumer() {
		ResponseEntity<?> response = http.postForEntity("/helloConsumer", "foobar", Void.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertFalse(response.hasBody());
	}

	@Test
	void helloConsumers() {
		ResponseEntity<?> response = http.postForEntity("/helloConsumer", List.of("foo", "bar", "baz"), Void.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertFalse(response.hasBody());
	}

	@Test
	void helloFunction() {
		ResponseEntity<String> response = http.postForEntity("/helloFunction", "foobar", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, foobar!", response.getBody());
	}

	@Test
	void helloFunctions() {
		ResponseEntity<String[]> response = http.postForEntity("/helloFunction", List.of("foo", "bar", "baz"),
				String[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertArrayEquals(new String[] { "Hello, foo!", "Hello, bar!", "Hello, baz!" }, response.getBody());
	}

	@Test
	void helloFunctionWithPath() {
		ResponseEntity<String> response = http.getForEntity("/helloFunction/foobar", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, foobar!", response.getBody());
	}

}
