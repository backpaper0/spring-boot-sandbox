package com.example.fn;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class FnApplicationTest {

	@Autowired
	private TestRestTemplate rest;

	@Test
	void hello() throws Exception {
		ResponseEntity<String> response = rest.exchange(RequestEntity.post("/hello").body("foobar"),
				String.class);
		assertEquals("Hello, foobar!", response.getBody());
	}
}
