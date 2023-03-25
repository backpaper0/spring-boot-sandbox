package com.example;

import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

public class PortBinding implements EnvironmentPostProcessor {

	@Override
	public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
		Integer port = environment.getProperty("port", Integer.class);
		if (port != null) {
			Map<String, Object> source = Map.of("server.port", port);
			environment.getPropertySources().addLast(new MapPropertySource("portBinding", source));
		}
	}
}
