package com.example.routing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoutingDataSourceApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(RoutingDataSourceApplication.class, args);
	}
}
