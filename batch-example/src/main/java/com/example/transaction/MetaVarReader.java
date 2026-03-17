package com.example.transaction;

import java.util.Iterator;
import java.util.stream.Stream;

import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.NonTransientResourceException;
import org.springframework.batch.infrastructure.item.ParseException;
import org.springframework.batch.infrastructure.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class MetaVarReader implements ItemReader<String> {

	private final Iterator<String> iterator = Stream.of("foo", "bar", "baz", "qux", "quux", "corge",
			"grault", "garply", "waldo", "fred", "plugh", "xyzzy", "thud").iterator();

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		if (iterator.hasNext()) {
			return iterator.next();
		}
		return null;
	}
}
