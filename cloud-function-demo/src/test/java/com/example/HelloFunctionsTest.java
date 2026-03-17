package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HelloFunctionsTest {

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
	void helloSupplier() {
		ResponseEntity<String> response = http.getForEntity("http://localhost:" + port + "/helloSupplier", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, world!", response.getBody());
	}

	@Test
	void helloConsumer() {
		ResponseEntity<?> response = http.postForEntity("http://localhost:" + port + "/helloConsumer", "foobar", Void.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertFalse(response.hasBody());
	}

	@Test
	void helloConsumers() {
		ResponseEntity<?> response = http.postForEntity("http://localhost:" + port + "/helloConsumer", List.of("foo", "bar", "baz"), Void.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertFalse(response.hasBody());
	}

	@Test
	void helloFunction() {
		ResponseEntity<String> response = http.postForEntity("http://localhost:" + port + "/helloFunction", "foobar", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, foobar!", response.getBody());
	}

	@Test
	void helloFunctions() {
		ResponseEntity<String[]> response = http.postForEntity("http://localhost:" + port + "/helloFunction", List.of("foo", "bar", "baz"),
				String[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertArrayEquals(new String[] { "Hello, foo!", "Hello, bar!", "Hello, baz!" }, response.getBody());
	}

	@Test
	void helloFunctionWithPath() {
		ResponseEntity<String> response = http.getForEntity("http://localhost:" + port + "/helloFunction/foobar", String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Hello, foobar!", response.getBody());
	}

}
