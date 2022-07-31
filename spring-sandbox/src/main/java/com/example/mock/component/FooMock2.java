package com.example.mock.component;

import org.springframework.stereotype.Component;

import com.example.mock.annotation.MyComponentMock;

@Component
@MyComponentMock(name = "com.example.mock.component.Foo", havingValue = "mock2")
public class FooMock2 implements Foo {

	@Override
	public String get() {
		return "mock2";
	}
}
