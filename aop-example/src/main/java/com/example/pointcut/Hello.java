package com.example.pointcut;

import org.springframework.stereotype.Component;

import com.example.Asterisk;

@Component
@Asterisk
public class Hello {

	public String say() {
		return "Hello Pointcut";
	}
}
