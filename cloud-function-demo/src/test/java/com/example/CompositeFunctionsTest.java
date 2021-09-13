package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CompositeFunctionsTest {

	@Autowired
	private TestRestTemplate http;

	@Test
	void uppercase() {
		String response = http.getForObject("/uppercase/foobar", String.class);
		assertEquals("FOOBAR", response);
	}

	@Test
	void reverse() {
		String response = http.getForObject("/reverse/foobar", String.class);
		assertEquals("raboof", response);
	}

	@Test
	void uppercaseReverse() {
		String response = http.getForObject("/uppercase,reverse/foobar", String.class);
		assertEquals("RABOOF", response);
	}
}
