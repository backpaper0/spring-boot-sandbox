package com.example.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "example")
@Data
public class ExampleBatchProperties {

	private int chunkSize;
	private Integer errorId;
}
