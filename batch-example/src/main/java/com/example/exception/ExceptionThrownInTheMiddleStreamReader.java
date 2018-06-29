package com.example.exception;

import java.util.Iterator;
import java.util.function.Function;
import java.util.stream.IntStream;

import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

public class ExceptionThrownInTheMiddleStreamReader implements ItemStreamReader<Integer> {

    private final Iterator<Integer> iterator = IntStream.rangeClosed(1, 10)
            .mapToObj(Integer::valueOf).iterator();

    private final Function<String, Exception> exception;

    public ExceptionThrownInTheMiddleStreamReader(final Function<String, Exception> exception) {
        this.exception = exception;
    }

    @Override
    public void open(final ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("*** open ***");
    }

    @Override
    public void update(final ExecutionContext executionContext) throws ItemStreamException {
        System.out.println("*** update ***");
    }

    @Override
    public void close() throws ItemStreamException {
        System.out.println("*** close ***");
    }

    @Override
    public Integer read() throws Exception, UnexpectedInputException, ParseException,
            NonTransientResourceException {
        System.out.println("*** read ***");
        if (iterator.hasNext()) {
            final int a = iterator.next();
            if (a == 8) {
                throw exception.apply(String.valueOf(a));
            }
            return a;
        }
        return null;
    }
}
