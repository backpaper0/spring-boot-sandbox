package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.test.StepVerifier;

public class FunctionalEndpointsTest {

	WebTestClient client;

	@BeforeEach
	void setUp() {
		client = WebTestClient.bindToRouterFunction(new FunctionalEndpoints().routerFunction())
				.build();
	}

	@Test
	void simple() {
		client.get().uri("/simple").exchange().expectBody(String.class).isEqualTo("hello world");
	}

	@Test
	void sse() {
		final var returnResult = client.get().uri("/sse")
				.exchange()
				.returnResult(String.class);

		StepVerifier.create(returnResult.getResponseBody())
				.expectNext("1", "2", "3", "4", "5")
				.verifyComplete();
	}
}
