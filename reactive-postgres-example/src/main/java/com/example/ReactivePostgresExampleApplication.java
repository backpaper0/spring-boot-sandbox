package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReactivePostgresExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(ReactivePostgresExampleApplication.class, args);
	}
}
