package com.example.common.httpclient;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "example.api")
@Data
public class RestTemplateProperties {

	private String baseUri;
}
