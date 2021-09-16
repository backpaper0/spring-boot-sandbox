package com.example;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DemoObject1 {

	private static final Logger logger = LoggerFactory.getLogger(DemoObject1.class);

	private final String value = LocalDateTime.now().toString();

	public String getValue() {
		logger.info("demoObject1: class={}, hashCode={}", getClass(), System.identityHashCode(this));
		return value;
	}
}
