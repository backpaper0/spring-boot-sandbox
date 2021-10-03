package com.example;

import org.springframework.stereotype.Component;

@Component
public class MyService {

	private final Foobar foobar;

	public MyService(Foobar foobar) {
		this.foobar = foobar;
	}

	public String foobar() {
		return foobar.foo() + foobar.bar();
	}
}
