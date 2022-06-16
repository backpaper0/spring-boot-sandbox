package com.example.infra;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.example.repository.ApiTokenRepository;

@Component
public class InMemoryApiTokenRepository implements ApiTokenRepository {

	private final Map<String, String> apiTokens = new HashMap<>();

	@Override
	public String generateApiToken(String username) {
		String apiToken = UUID.randomUUID().toString();
		apiTokens.put(apiToken, username);
		return apiToken;
	}

	@Override
	public String get(String apiToken) {
		return apiTokens.get(apiToken);
	}
}
