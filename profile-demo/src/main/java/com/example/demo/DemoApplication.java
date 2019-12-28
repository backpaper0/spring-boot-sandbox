package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements org.springframework.boot.CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

    @org.springframework.beans.factory.annotation.Value("${hello}")
    private String hello;

    @Override
    public void run(String[] args) {
        System.out.println(hello); 
    }
}
