package com.example.multitm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MultiTransactionManagerApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(MultiTransactionManagerApplication.class, args);
	}
}
