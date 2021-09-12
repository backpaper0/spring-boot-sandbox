package com.example.fn;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class FnApplication {

	public static void main(final String[] args) {
		SpringApplication.run(FnApplication.class, args);
	}

	@Bean
	public Function<String, String> hello() {
		return s -> String.format("Hello, %s!", s);
	}

	@Bean
	public Function<Mono<String>, Mono<String>> upper() {
		return mono -> mono.map(String::toUpperCase);
	}
}
