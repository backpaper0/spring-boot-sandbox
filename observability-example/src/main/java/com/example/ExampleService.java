package com.example;

import org.springframework.stereotype.Service;

import io.micrometer.observation.annotation.Observed;

@Service
public class ExampleService {

	@Observed
	public String sayHello(String name) {
		return "Hello, " + name + "!";
	}
}
