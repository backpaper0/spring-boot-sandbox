package com.example.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class LoginFailureCountUpdaterProperties {

	private int maxLoginFailureCount;

	public int getMaxLoginFailureCount() {
		return maxLoginFailureCount;
	}

	public void setMaxLoginFailureCount(int maxLoginFailureCount) {
		this.maxLoginFailureCount = maxLoginFailureCount;
	}
}
