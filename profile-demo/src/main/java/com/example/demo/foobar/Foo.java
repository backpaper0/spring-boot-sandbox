package com.example.demo.foobar;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("default")
public class Foo implements Foobar {

	@Override
	public String get() {
		return "foo";
	}
}
