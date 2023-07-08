package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class App {

	@SuppressWarnings("resource")
	public static void main(final String[] args) {
		SpringApplication.run(App.class, args);
	}
}
