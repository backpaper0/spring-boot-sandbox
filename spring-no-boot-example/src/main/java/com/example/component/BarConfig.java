package com.example.component;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BarConfig {

	@Bean
	public Bar bar() {
		return new Bar("bar");
	}
}
