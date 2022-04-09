package com.example.importconfig.config2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.misc.Baz;

@Configuration(proxyBeanMethods = false)
@Import(Config2c.class)
public class Config2a {

	@Bean
	public Baz bazA() {
		return new Baz("A");
	}
}
