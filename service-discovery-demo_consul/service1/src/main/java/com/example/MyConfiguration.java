package com.example;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MyConfiguration {

	private final MyProperties properties;

	public MyConfiguration(MyProperties properties) {
		this.properties = properties;
	}

	@Bean
	@LoadBalanced
	public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(properties.getConnectTimeout())
				.setReadTimeout(properties.getReadTimeout())
				.build();
	}
}
