package com.example.importconfig.config2;

import org.springframework.context.annotation.Bean;

import com.example.misc.Baz;

public class Config2c {

	private boolean called;

	@Bean
	public Baz bazC() {
		if (called) {
			throw new RuntimeException();
		}
		called = true;
		return new Baz("C");
	}
}
