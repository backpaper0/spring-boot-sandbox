package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class App {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
}
