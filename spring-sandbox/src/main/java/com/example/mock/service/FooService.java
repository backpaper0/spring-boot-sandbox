package com.example.mock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.mock.component.Foo;

@Service
public class FooService {

	@Autowired
	private Foo foo;

	public String get() {
		return foo.get();
	}
}
