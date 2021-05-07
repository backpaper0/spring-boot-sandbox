package com.example;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ConfigurationPropertiesScan
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	public ApplicationRunner runner(MyProperties properties) {
		return args -> {
			System.out.printf("foo=%s%n", properties.getFoo());
			System.out.printf("bar=%d%n", properties.getBar());
		};
	}
}
