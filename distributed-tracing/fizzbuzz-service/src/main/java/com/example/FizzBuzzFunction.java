package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Configuration(proxyBeanMethods = false)
@ConfigurationProperties(prefix = "my")
public class FizzBuzzFunction {

	private final WebClient http;
	private String fizzUrl;
	private String buzzUrl;

	public FizzBuzzFunction(WebClient http) {
		this.http = http;
	}

	@Bean
	public RouterFunction<ServerResponse> fizzbuzz() {
		return RouterFunctions.route().GET("/fizzbuzz", request -> {
			Mono<String> fizz = http.get().uri(fizzUrl).retrieve().bodyToMono(String.class);
			Mono<String> buzz = http.get().uri(buzzUrl).retrieve().bodyToMono(String.class);
			Mono<String> fizzbuzz = fizz.zipWith(buzz, String::concat);
			return fizzbuzz.flatMap(ServerResponse.ok()::bodyValue);
		}).build();
	}

	public void setFizzUrl(String fizzUrl) {
		this.fizzUrl = fizzUrl;
	}

	public void setBuzzUrl(String buzzUrl) {
		this.buzzUrl = buzzUrl;
	}
}
