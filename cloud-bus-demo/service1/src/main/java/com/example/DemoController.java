package com.example;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

	private static final Logger logger = LoggerFactory.getLogger(DemoController.class);

	private final DemoProperties properties;
	private final DemoObject demoObject;

	public DemoController(DemoProperties properties, DemoObject demoObject) {
		this.properties = properties;
		this.demoObject = demoObject;
	}

	@GetMapping("/demo")
	public Object get() {
		logger.info("properties: class={}, hashCode={}", properties.getClass(), System.identityHashCode(properties));
		logger.info("demoObject: class={}, hashCode={}", demoObject.getClass(), System.identityHashCode(demoObject));

		return Map.of("foo", properties.getFoo(), "bar", properties.getBar(), "value", demoObject.getValue());
	}
}
