package com.example;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class DemoObject {

	private static final Logger logger = LoggerFactory.getLogger(DemoObject.class);

	private final String value = LocalDateTime.now().toString();

	public String getValue() {
		logger.info("demoObject: class={}, hashCode={}", getClass(), System.identityHashCode(this));
		return value;
	}
}
