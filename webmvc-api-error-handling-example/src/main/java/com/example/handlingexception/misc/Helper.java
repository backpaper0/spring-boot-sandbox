package com.example.handlingexception.misc;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

@Component("helper")
public class Helper {

	private final AtomicInteger counter = new AtomicInteger();

	public boolean helperMethod() throws Exception {
		if (counter.getAndIncrement() % 2 == 1) {
			throw new Exception();
		}
		return true;
	}
}
