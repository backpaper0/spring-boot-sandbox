package com.example.profile.component;

public class FoobarService {

	private final Foobar foobar;

	public FoobarService(Foobar foobar) {
		this.foobar = foobar;
	}

	public String get() {
		return foobar.get();
	}
}
