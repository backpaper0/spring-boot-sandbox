package com.example.webfluxexample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringJUnitConfig(FunctionalEndpoints.class)
class FunctionalEndpointsTest {

	@Autowired
	private FunctionalEndpoints functionalEndpoints;

	private WebTestClient client;

	@BeforeEach
	void setUpClient() {
		client = WebTestClient
				.bindToRouterFunction(functionalEndpoints.routerFunction())
				.build();
	}

	@Test
	public void add() {

		final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("a", "2");
		form.add("b", "3");

		client.post().uri("/add")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED).syncBody(form)
				.exchange()
				.expectHeader().contentType(MediaType.TEXT_PLAIN)
				.expectBody(String.class).isEqualTo("5");
	}
}
