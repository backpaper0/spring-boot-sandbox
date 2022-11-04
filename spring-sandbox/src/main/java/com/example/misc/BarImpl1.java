package com.example.misc;

import java.util.Objects;

public class BarImpl1 implements Bar {

	private final String name;

	public BarImpl1(String name) {
		this.name = name;
	}

	public BarImpl1() {
		this("");
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		var other = getClass().cast(obj);
		return Objects.equals(name, other.name);
	}

	@Override
	public String toString() {
		return "BarImpl1(" + name + ")";
	}
}
