package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.retry.RetryContext;
import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.support.RetrySynchronizationManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	private static final Logger logger = LoggerFactory.getLogger(ExampleController.class);

	@RequestMapping("/")
	@Retryable
	public String hello(@RequestParam(defaultValue = "0") int count) {
		RetryContext context = RetrySynchronizationManager.getContext();
		if (context != null) {
			logger.info("Retry count = {}", context.getRetryCount());
			if (context.getRetryCount() < count) {
				throw new RuntimeException();
			}
		}
		return "hello";
	}
}
