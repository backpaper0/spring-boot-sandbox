package com.example;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class InMemoryApiTokenRepository {

	private final Map<String, String> apiTokens = new HashMap<>();

	public String generateApiToken(String username) {
		String apiToken = UUID.randomUUID().toString();
		apiTokens.put(apiToken, username);
		return apiToken;
	}

	public String get(String apiToken) {
		return apiTokens.get(apiToken);
	}
}
