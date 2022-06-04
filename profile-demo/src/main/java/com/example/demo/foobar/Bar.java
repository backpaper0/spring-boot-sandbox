package com.example.demo.foobar;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("production")
public class Bar implements Foobar {

	@Override
	public String get() {
		return "bar";
	}
}
