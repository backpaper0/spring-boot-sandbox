package com.example.misc;

public class BarImpl1 implements Bar {

	private final String name;

	public BarImpl1(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BarImpl1(" + name + ")";
	}
}
