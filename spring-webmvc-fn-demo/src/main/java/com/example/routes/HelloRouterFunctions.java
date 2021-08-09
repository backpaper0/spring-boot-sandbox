package com.example.routes;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class HelloRouterFunctions {

	@Bean
	public RouterFunction<ServerResponse> helloRouterFunction() {
		return RouterFunctions.route()
				.GET("/hello", this::getHello)
				.POST("/hello", RequestPredicates.contentType(MediaType.APPLICATION_FORM_URLENCODED), this::postHello)
				.build();
	}

	private ServerResponse getHello(ServerRequest request) {
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Map.of("message", "Hello, world!"));
	}

	private ServerResponse postHello(ServerRequest request) {
		String name = request.param("name").get();
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(Map.of("message", "Hello, " + name + "!"));
	}
}
