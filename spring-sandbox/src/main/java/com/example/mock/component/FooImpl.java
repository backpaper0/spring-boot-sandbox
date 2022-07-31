package com.example.mock.component;

import org.springframework.stereotype.Component;

import com.example.mock.annotation.MyComponentImpl;

@Component
@MyComponentImpl(name = "com.example.mock.component.Foo", havingValue = "impl")
public class FooImpl implements Foo {

	@Override
	public String get() {
		return "impl";
	}
}
