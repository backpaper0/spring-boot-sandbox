package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class Hello {

	private static final Logger logger = LoggerFactory.getLogger(Hello.class);

	private final RestTemplate restTemplate;
	private final HelloProps props;
	private final Resilience4JCircuitBreakerFactory circuitBreakerFactory;

	public Hello(RestTemplate restTemplate, Resilience4JCircuitBreakerFactory circuitBreakerFactory, HelloProps props) {
		this.restTemplate = restTemplate;
		this.circuitBreakerFactory = circuitBreakerFactory;
		this.props = props;
	}

	public String get() {
		return circuitBreakerFactory.create("hello").run(this::hello, this::defaultHello);
	}

	String hello() {
		logger.info("Start hello()");
		try {
			return restTemplate.getForObject(props.getBackendApi(), String.class);
		} catch (Throwable t) {
			logger.info("Exception occurred");
			throw t;
		}
	}

	String defaultHello(Throwable t) {
		logger.info("Fallback to defaultHello()");
		return "Hello, Circuit Breaker!!!\n";
	}
}