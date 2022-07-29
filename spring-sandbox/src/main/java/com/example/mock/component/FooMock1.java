package com.example.mock.component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "com.example.mock.component.Foo", havingValue = "mock1")
public class FooMock1 implements Foo {

	@Override
	public String get() {
		return "mock1";
	}
}
