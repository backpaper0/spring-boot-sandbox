package com.example.webmvc.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ModelExampleApplicationTest {

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
	void home() throws Exception {
		final ModelExampleModel model = http.getForObject("http://localhost:" + port + "/", ModelExampleModel.class);
		assertEquals(new ModelExampleModel("default"), model);
	}

	@Test
	void foobar() throws Exception {
		final Object model = http.exchange(RequestEntity.get("http://localhost:" + port + "/foobar").build(),
				new ParameterizedTypeReference<Map<String, ModelExampleModel>>() {
				}).getBody();
		assertEquals(
				Map.of("foo", new ModelExampleModel("foo"), "bar", new ModelExampleModel("bar")),
				model);
	}
}
