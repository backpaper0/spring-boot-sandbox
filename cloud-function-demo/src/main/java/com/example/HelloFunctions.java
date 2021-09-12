package com.example;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HelloFunctions {

	@Bean
	public Supplier<String> helloSupplier() {
		return () -> "Hello, world!";
	}

	@Bean
	public Function<String, String> helloFunction() {
		return name -> "Hello, " + name + "!";
	}

	@Bean
	public Consumer<String> helloConsumer() {
		Logger logger = LoggerFactory.getLogger(HelloFunctions.class.getName() + ".helloConsumer");
		return message -> logger.info("{}", message);
	}
}
