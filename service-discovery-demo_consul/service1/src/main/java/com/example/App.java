package com.example;

import java.time.Duration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ConfigurationProperties(prefix = "my")
public class App {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	private Duration connectTimeout;
	private Duration readTimeout;

	@Bean
	@LoadBalanced
	public RestTemplate loadBalancedRestTemplate(RestTemplateBuilder builder) {
		return builder.setConnectTimeout(connectTimeout).setReadTimeout(readTimeout).build();
	}

	public void setConnectTimeout(Duration connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public void setReadTimeout(Duration readTimeout) {
		this.readTimeout = readTimeout;
	}
}
