package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MetricsExampleApplication {

	@SuppressWarnings("resource")
	public static void main(final String[] args) {
		SpringApplication.run(MetricsExampleApplication.class, args);
	}
}
