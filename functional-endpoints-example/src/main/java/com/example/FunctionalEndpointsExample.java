package com.example;

import java.time.Duration;

import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.netty.http.server.HttpServer;

public class FunctionalEndpointsExample {

	public static void main(final String[] args) throws Exception {

		final RouterFunction<ServerResponse> routerFunction = HelloHandlers.routerFunction()
				.and(PersonHandlers.routerFunction());

		final HttpHandler handler = RouterFunctions.toHttpHandler(routerFunction);
		final ReactorHttpHandlerAdapter adapter = new ReactorHttpHandlerAdapter(handler);
		HttpServer.create()
				.host("localhost")
				.port(8080)
				.handle(adapter)
				.bindUntilJavaShutdown(Duration.ofDays(1), server -> {
				});
	}
}
