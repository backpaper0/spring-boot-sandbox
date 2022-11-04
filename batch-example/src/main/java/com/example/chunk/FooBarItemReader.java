package com.example.chunk;

import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class FooBarItemReader implements ItemReader<String> {

	private final Iterator<String> iterator = Stream.of("foo", "bar", "baz", "qux").iterator();

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}
}
