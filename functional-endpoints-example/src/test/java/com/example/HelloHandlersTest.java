package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

class HelloHandlersTest {

	WebTestClient client = WebTestClient
			.bindToRouterFunction(HelloHandlers.routerFunction())
			.build();

	@Test
	void hello() {
		client.get().uri("/hello").exchange()
				.expectStatus().isOk()
				.expectHeader().contentTypeCompatibleWith(MediaType.TEXT_PLAIN)
				.expectBody(String.class).isEqualTo("Hello, world!");
	}
}
