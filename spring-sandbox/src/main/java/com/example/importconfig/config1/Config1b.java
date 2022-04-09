package com.example.importconfig.config1;

import org.springframework.context.annotation.Bean;

import com.example.misc.BarImpl1;

public class Config1b {

	@Bean
	public BarImpl1 bar() {
		return new BarImpl1("");
	}
}
