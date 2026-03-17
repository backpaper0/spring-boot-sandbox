package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompositeFunctionsTest {

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
	void uppercase() {
		String response = http.getForObject("http://localhost:" + port + "/uppercase/foobar", String.class);
		assertEquals("FOOBAR", response);
	}

	@Test
	void reverse() {
		String response = http.getForObject("http://localhost:" + port + "/reverse/foobar", String.class);
		assertEquals("raboof", response);
	}

	@Test
	void uppercaseReverse() {
		String response = http.getForObject("http://localhost:" + port + "/uppercase,reverse/foobar", String.class);
		assertEquals("RABOOF", response);
	}
}
