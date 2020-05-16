package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.PersonHandlers.Person;

class PersonHandlersTest {

	WebTestClient client = WebTestClient
			.bindToRouterFunction(PersonHandlers.routerFunction())
			.build();

	@Test
	void getPerson() {
		client.get().uri("/people/hoge").exchange()
				.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				.expectBody().jsonPath("name", "hoge");
	}

	@Test
	void postPerson() {
		client.post().uri("/people").contentType(MediaType.APPLICATION_JSON)
				.syncBody(new Person("foobar")).exchange()
				.expectHeader().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)
				.expectBody().jsonPath("name", "foobar");
	}
}
