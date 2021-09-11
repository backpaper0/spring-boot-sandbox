package com.example;

import java.net.URI;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "hello")
public class HelloProps {

	private URI backendApi;

	public URI getBackendApi() {
		return backendApi;
	}

	public void setBackendApi(URI backendApi) {
		this.backendApi = backendApi;
	}
}