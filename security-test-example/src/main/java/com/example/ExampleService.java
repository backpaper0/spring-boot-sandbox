package com.example;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

	public String get(Authentication a, String s) {
		return Optional.ofNullable(a).map(Authentication::getName).orElse("") + ":" + s;
	}
}