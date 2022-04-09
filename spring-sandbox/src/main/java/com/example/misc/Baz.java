package com.example.misc;

public class Baz {

	private final String name;

	public Baz(String name) {
		this.name = name;
	}

	public Baz() {
		this("");
	}

	@Override
	public String toString() {
		return "Baz(" + name + ")";
	}
}
