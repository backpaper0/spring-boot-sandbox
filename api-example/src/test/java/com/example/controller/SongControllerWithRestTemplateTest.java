package com.example.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SongControllerWithRestTemplateTest extends RestControllerTestBase {

	@Autowired
	TestRestTemplate http;

	@Test
	void testGetList() throws Exception {
		var request = RequestEntity.get("/songs").build();

		var response = http.exchange(request, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		JSONAssert.assertEquals(
				read("expected-list.json"),
				response.getBody(), false);
	}

	@Test
	void testCreate() throws Exception {
		var request = RequestEntity.post("/songs")
				.contentType(MediaType.APPLICATION_JSON)
				.body(read("create.json"));

		var response = http.exchange(request, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		JSONAssert.assertEquals(
				read("expected-create.json"),
				response.getBody(), false);
	}
}
