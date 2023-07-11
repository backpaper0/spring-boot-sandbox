package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.huxhorn.sulky.ulid.ULID;

@Configuration
public class ULIDConfig {

	@Bean
	ULID ulid() {
		return new ULID();
	}
}
