package com.example.interceptor;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeSupplier {

	public LocalDateTime now() {
		return LocalDateTime.now();
	}
}
