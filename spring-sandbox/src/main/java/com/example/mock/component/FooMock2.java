package com.example.mock.component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "com.example.mock.component.Foo", havingValue = "mock2")
public class FooMock2 implements Foo {

	@Override
	public String get() {
		return "mock2";
	}
}
