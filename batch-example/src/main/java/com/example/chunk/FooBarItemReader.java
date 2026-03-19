package com.example.chunk;

import java.util.Iterator;
import java.util.stream.Stream;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.NonTransientResourceException;
import org.springframework.batch.infrastructure.item.ParseException;
import org.springframework.batch.infrastructure.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@Component
public class FooBarItemReader implements ItemReader<String> {

    private final Iterator<String> iterator =
            Stream.of("foo", "bar", "baz", "qux").iterator();

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }
}
