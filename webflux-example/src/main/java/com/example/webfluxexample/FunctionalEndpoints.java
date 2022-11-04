package com.example.webfluxexample;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.*;

import java.util.Objects;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class FunctionalEndpoints {

	@Bean
	public RouterFunction<ServerResponse> routerFunction() {
		return route(POST("/add"), this::handle);
	}

	private Mono<ServerResponse> handle(final ServerRequest request) {
		// curl localhost:8080/add -d a=2 -d b=3

		final Mono<String> s = request.formData()
				.flatMapMany(form -> Flux.just("a", "b").map(form::getFirst))
				.map(Integer::parseInt)
				.reduce(Integer::sum)
				.map(Objects::toString);

		return ServerResponse.ok().contentType(MediaType.TEXT_PLAIN).body(s, String.class);
	}
}
