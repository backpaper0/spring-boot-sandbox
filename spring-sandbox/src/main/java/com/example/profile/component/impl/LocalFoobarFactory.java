package com.example.profile.component.impl;

import org.springframework.stereotype.Component;

import com.example.profile.annotation.LocalEnv;
import com.example.profile.component.Foobar;
import com.example.profile.component.FoobarFactory;

@Component
@LocalEnv
public class LocalFoobarFactory implements FoobarFactory {

	@Override
	public Foobar create(String name) {
		return () -> name + ":local";
	}
}
