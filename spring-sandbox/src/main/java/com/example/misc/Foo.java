package com.example.misc;

public class Foo {

	private final String name;

	public Foo(String name) {
		this.name = name;
	}

	public Foo() {
		this("");
	}

	@Override
	public String toString() {
		return "Foo(" + name + ")";
	}
}
