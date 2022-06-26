package com.example.demo.component.mock;

import org.springframework.stereotype.Component;

import com.example.demo.component.ComponentInterface;
import com.example.demo.component.annotation.ComponentMock;

@Component
@ComponentMock
class ComponentInterfaceMock implements ComponentInterface {

	@Override
	public String get() {
		return "mock";
	}
}
