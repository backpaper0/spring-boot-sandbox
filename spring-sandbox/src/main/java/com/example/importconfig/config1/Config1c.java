package com.example.importconfig.config1;

import org.springframework.context.annotation.Bean;

import com.example.misc.BarImpl2;

public class Config1c {

	@Bean
	public BarImpl2 bar() {
		return new BarImpl2("");
	}
}
