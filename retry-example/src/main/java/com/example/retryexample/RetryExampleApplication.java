package com.example.retryexample;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@SpringBootApplication
@EnableRetry
@RestController
public class RetryExampleApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(RetryExampleApplication.class, args);
	}

	private static final Logger logger = LoggerFactory.getLogger(RetryExampleApplication.class);

	private final Obj obj;

	public RetryExampleApplication(final Obj obj) {
		this.obj = Objects.requireNonNull(obj);
	}

	@RequestMapping
	@Retryable
	public String hello(@RequestParam(defaultValue = "0") final int count) {
		if (logger.isInfoEnabled()) {
			logger.info("try");
		}
		if (obj.getCount() < count) {
			obj.increment();
			throw new RuntimeException();
		}
		return "hello";
	}

	@Component
	@RequestScope
	public static class Obj {

		private int count;

		public void increment() {
			count++;
		}

		public int getCount() {
			return count;
		}
	}
}
