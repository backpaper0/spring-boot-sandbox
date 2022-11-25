package com.example;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import java.util.Objects;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.annotation.JsonProperty;

import reactor.core.publisher.Mono;

public class PersonHandlers {

	Mono<ServerResponse> getPerson(final ServerRequest request) {
		final String name = request.pathVariable("name");
		return ServerResponse.ok().bodyValue(new Person(name));
	}

	Mono<ServerResponse> postPerson(final ServerRequest request) {
		return ServerResponse.ok().body(request.bodyToMono(Person.class), Person.class);
	}

	static RouterFunction<ServerResponse> routerFunction() {
		final PersonHandlers handlers = new PersonHandlers();
		return RouterFunctions
				.route(GET("/people/{name}"), handlers::getPerson)
				.andRoute(POST("/people").and(accept(MediaType.APPLICATION_JSON)),
						handlers::postPerson);
	}

	static class Person {

		private final String name;

		public Person(@JsonProperty("name") final String name) {
			this.name = Objects.requireNonNull(name);
		}

		public String getName() {
			return name;
		}
	}
}
