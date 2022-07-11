package com.example.profile.component.impl;

import org.springframework.stereotype.Component;

import com.example.profile.annotation.ServerEnv;
import com.example.profile.component.Foobar;
import com.example.profile.component.FoobarFactory;

@Component
@ServerEnv
public class ServerFoobarFactory implements FoobarFactory {

	@Override
	public Foobar create(String name) {
		return () -> name + ":server";
	}
}
