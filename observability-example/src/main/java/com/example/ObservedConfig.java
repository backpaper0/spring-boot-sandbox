package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;

@Configuration
public class ObservedConfig {

	@Bean
	public ObservedAspect observedAspect(ObservationRegistry registry) {
		return new ObservedAspect(registry);
	}
}
