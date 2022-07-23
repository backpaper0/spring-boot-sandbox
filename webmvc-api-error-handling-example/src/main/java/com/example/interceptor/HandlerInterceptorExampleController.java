package com.example.interceptor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/handler-interceptor-example")
public class HandlerInterceptorExampleController {

	@GetMapping
	public String get() {
		return ClassNameHolder.get();
	}
}
