package com.example;

import java.util.Map;

import org.springframework.web.service.annotation.GetExchange;

public interface Hello {

	@GetExchange(url = "/hello")
	Map<String, String> sayHello();
}
