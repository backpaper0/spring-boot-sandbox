package com.example.misc;

import java.util.Objects;

public class Baz {

	private final String name;

	public Baz(String name) {
		this.name = name;
	}

	public Baz() {
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
		return "Baz(" + name + ")";
	}
}
