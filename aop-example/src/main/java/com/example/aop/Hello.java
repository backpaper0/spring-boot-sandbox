package com.example.aop;

import org.springframework.stereotype.Component;

import com.example.Asterisk;

@Component
public class Hello {

	@Asterisk
	public String say() {
		return "Hello AOP";
	}
}
