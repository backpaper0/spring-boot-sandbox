package com.example.demo;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;

@SpringBootApplication
@ConfigurationProperties
public class DemoApplication implements ApplicationRunner {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	private String hello;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(hello);
	}

	public String getHello() {
		return hello;
	}

	public void setHello(String hello) {
		this.hello = hello;
	}
}
