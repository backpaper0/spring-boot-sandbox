package com.example.resttemplateexample;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.constraints.Size;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.validation.BindException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ValidationTest {

	@LocalServerPort
	int port;

	@Autowired
	RestTemplate http;

	@Test
	void test1() {
		Hello1 hello = http.getForObject("http://localhost:" + port + "/hello", Hello1.class);
		assertEquals("Hello World", hello.getMessage());
	}

	@Test
	void test2() {
		RestClientException exception = assertThrows(RestClientException.class,
				() -> http.getForObject("http://localhost:" + port + "/hello", Hello2.class));
		BindException bindException = (BindException) exception.getCause();
		assertTrue(bindException.hasFieldErrors("message"));
	}

	static class Hello1 {

		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}

	static class Hello2 {

		@Size(max = 10)
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
	}
}
