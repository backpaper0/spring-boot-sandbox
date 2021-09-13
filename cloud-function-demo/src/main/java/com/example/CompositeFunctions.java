package com.example;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CompositeFunctions {

	@Bean
	public Function<String, String> uppercase() {
		return String::toUpperCase;
	}

	@Bean
	public Function<String, String> reverse() {
		return s -> {
			char[] cs = s.toCharArray();
			char[] data = new char[cs.length];
			for (int i = 0; i < data.length; i++) {
				data[i] = cs[cs.length - i - 1];
			}
			return String.valueOf(data);
		};
	}
}
