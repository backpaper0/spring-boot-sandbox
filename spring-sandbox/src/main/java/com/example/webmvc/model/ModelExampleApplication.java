package com.example.webmvc.model;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class ModelExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(ModelExampleApplication.class, args);
	}
}
