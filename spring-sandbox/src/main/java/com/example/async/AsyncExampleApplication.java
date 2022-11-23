package com.example.async;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AsyncExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(AsyncExampleApplication.class, args);
	}

	@Bean
	ExecutorService altExecutor() {
		return Executors.newSingleThreadExecutor(r -> new Thread(r, "alternative executor"));
	}
}
