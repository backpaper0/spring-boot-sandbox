package com.example;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class DemoObject2 {

	private static final Logger logger = LoggerFactory.getLogger(DemoObject2.class);

	private final String value = LocalDateTime.now().toString();

	public String getValue() {
		logger.info("demoObject2: class={}, hashCode={}", getClass(), System.identityHashCode(this));
		return value;
	}
}
