package com.example;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

public class HelloHandlers {

	Mono<ServerResponse> hello(final ServerRequest request) {
		return ServerResponse.ok().body(Mono.just("Hello, world!"), String.class);
	}

	static RouterFunction<ServerResponse> routerFunction() {
		final HelloHandlers handlers = new HelloHandlers();
		return RouterFunctions
				.route(GET("/hello"), handlers::hello);
	}
}
