package com.example;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class DemoController {

	@Value("${my.foo}")
	private String foo;
	@Value("${my.baz}")
	private String baz;

	@GetMapping("/demo")
	public Object get() {
		return Map.of("foo", foo, "baz", baz);
	}
}
