package com.example.webmvc.model;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ModelExampleApplicationTest {

	@Autowired
	TestRestTemplate http;

	@Test
	void home() throws Exception {
		final ModelExampleModel model = http.getForObject("/", ModelExampleModel.class);
		assertEquals(new ModelExampleModel("default"), model);
	}

	@Test
	void foobar() throws Exception {
		final Object model = http.exchange(RequestEntity.get(URI.create("/foobar")).build(),
				new ParameterizedTypeReference<Map<String, ModelExampleModel>>() {
				}).getBody();
		assertEquals(
				Map.of("foo", new ModelExampleModel("foo"), "bar", new ModelExampleModel("bar")),
				model);
	}
}
