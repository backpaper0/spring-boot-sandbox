package com.example.component;

import org.springframework.stereotype.Component;

import com.example.component.aop.BazAop;

@Component
@BazAop
public class Baz {

	public String name() {
		return "baz";
	}
}
