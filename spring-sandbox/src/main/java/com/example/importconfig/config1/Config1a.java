package com.example.importconfig.config1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.example.misc.Bar;
import com.example.misc.Foo;

@Configuration(proxyBeanMethods = false)
@Import(Config1b.class)
public class Config1a {

	@Bean
	public Foo foo(Bar bar) {
		return new Foo(bar.toString());
	}
}
