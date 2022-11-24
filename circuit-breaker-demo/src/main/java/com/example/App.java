package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

@SpringBootApplication
public class App {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	Customizer<Resilience4JCircuitBreakerFactory> loggingStateTransition() {
		Logger logger = LoggerFactory.getLogger("loggingStateTransition");
		Customizer<CircuitBreaker> rawCustomizer = circuitBreaker -> circuitBreaker.getEventPublisher()
				.onStateTransition(event -> {
					logger.info("{}", event);
				});
		Object key = new Object();
		Customizer<CircuitBreaker> onceCustomizer = Customizer.once(rawCustomizer, t -> key);
		return factory -> factory.addCircuitBreakerCustomizer(onceCustomizer, "hello");
	}
}
