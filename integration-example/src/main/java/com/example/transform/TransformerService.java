package com.example.transform;

import org.springframework.integration.annotation.Transformer;
import org.springframework.stereotype.Component;

@Component
public class TransformerService {

	@Transformer
	public String transform(String message) {
		return "*" + message + "*";
	}
}
