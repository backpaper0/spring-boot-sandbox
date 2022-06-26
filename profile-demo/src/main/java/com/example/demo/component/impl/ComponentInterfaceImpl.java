package com.example.demo.component.impl;

import org.springframework.stereotype.Component;

import com.example.demo.component.ComponentInterface;
import com.example.demo.component.annotation.ComponentImpl;

@Component
@ComponentImpl
class ComponentInterfaceImpl implements ComponentInterface {

	@Override
	public String get() {
		return "impl";
	}
}
