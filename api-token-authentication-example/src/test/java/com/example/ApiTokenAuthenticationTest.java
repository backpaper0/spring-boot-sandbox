package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "example.api-token=0000000000")
public class ApiTokenAuthenticationTest {

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
	void unauthorized() {
		var responseEntity = http.getForEntity("http://localhost:" + port + "/demo", Map.class);
		assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
	}

	@Test
	void ok() {
		var token = "0000000000";
		var requestEntity = RequestEntity.get("http://localhost:" + port + "/demo").header("Authorization", "Bearer " + token).build();
		var responseEntity = http.exchange(requestEntity, Map.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals("test", responseEntity.getBody().get("name"));
	}

	@Test
	void mistakeApiToken() {
		var token = "mistake";
		var requestEntity = RequestEntity.get("http://localhost:" + port + "/demo").header("Authorization", "Bearer " + token).build();
		var responseEntity = http.exchange(requestEntity, Map.class);
		assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
	}
}
