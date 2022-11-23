package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(JpaExampleApplication.class, args);
	}
}
