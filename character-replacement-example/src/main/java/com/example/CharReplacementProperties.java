package com.example;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app.char-replacement")
public class CharReplacementProperties {

	private Map<String, String> charMap = Map.of();

	public Map<String, String> getCharMap() {
		return charMap;
	}

	public void setCharMap(Map<String, String> charMap) {
		this.charMap = charMap;
	}
}
