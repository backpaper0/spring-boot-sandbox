package com.example.misc;

public class BarImpl2 implements Bar {

	private final String name;

	public BarImpl2(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BarImpl2(" + name + ")";
	}
}
