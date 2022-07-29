package com.example.mock.component;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "com.example.mock.component.Foo", havingValue = "impl")
public class FooImpl implements Foo {

	@Override
	public String get() {
		return "impl";
	}
}
