package com.example.common.httpclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.DefaultUriBuilderFactory.EncodingMode;

@Configuration
public class RestTemplateConfiguration {

	@Autowired
	private RestTemplateProperties properties;
	@Autowired
	private RoundTripTimeRecorder interceptor;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		RestTemplateCustomizer customizer = restTemplate -> {
			DefaultUriBuilderFactory handler = new DefaultUriBuilderFactory(properties.getBaseUri());
			handler.setEncodingMode(EncodingMode.URI_COMPONENT);
			restTemplate.setUriTemplateHandler(handler);
		};
		return builder
				.additionalCustomizers(customizer)
				.interceptors(interceptor)
				.build();
	}
}
