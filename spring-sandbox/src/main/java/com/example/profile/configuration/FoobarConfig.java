package com.example.profile.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.profile.component.FoobarFactory;
import com.example.profile.component.FoobarService;

@Configuration
public class FoobarConfig {

	@Autowired
	private FoobarFactory foobarFactory;

	@Bean
	public FoobarService foobarService() {
		return new FoobarService(foobarFactory.create("example"));
	}
}
