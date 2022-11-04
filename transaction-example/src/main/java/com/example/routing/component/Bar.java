package com.example.routing.component;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.example.routing.LookupKey;
import com.example.routing.Use;

@Component
@Transactional
@Use(LookupKey.BAR)
public class Bar {

	private final Foo foo;

	public Bar(final Foo foo) {
		this.foo = Objects.requireNonNull(foo);
	}

	public void bar() {
	}

	public void nest() {
		foo.foo();
	}
}