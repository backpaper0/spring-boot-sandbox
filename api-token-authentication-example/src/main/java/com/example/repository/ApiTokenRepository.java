package com.example.repository;

public interface ApiTokenRepository {

	String generateApiToken(String username);

	String get(String apiToken);
}
