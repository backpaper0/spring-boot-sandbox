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
	private final DemoObject1 demoObject1;
	private final DemoObject2 demoObject2;

	public DemoController(DemoProperties properties, DemoObject1 demoObject1, DemoObject2 demoObject2) {
		this.properties = properties;
		this.demoObject1 = demoObject1;
		this.demoObject2 = demoObject2;
	}

	@GetMapping("/demo")
	public Object get() {
		logger.info("controller: class={}, hashCode={}", this.getClass(), System.identityHashCode(this));
		logger.info("properties: class={}, hashCode={}", properties.getClass(), System.identityHashCode(properties));
		logger.info("demoObject1: class={}, hashCode={}", demoObject1.getClass(), System.identityHashCode(demoObject1));
		logger.info("demoObject2: class={}, hashCode={}", demoObject2.getClass(), System.identityHashCode(demoObject2));

		return Map.of(
				"foo", properties.getFoo(),
				"bar", properties.getBar(),
				"value1", demoObject1.getValue(),
				"value2", demoObject2.getValue());
	}
}
