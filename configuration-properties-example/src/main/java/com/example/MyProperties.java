package com.example;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties(prefix = "app")
public class MyProperties {

	private final String foo;
	private final int bar;

	@ConstructorBinding
	public MyProperties(String foo, int bar) {
		this.foo = foo;
		this.bar = bar;
	}

	public String getFoo() {
		return foo;
	}

	public int getBar() {
		return bar;
	}
}
