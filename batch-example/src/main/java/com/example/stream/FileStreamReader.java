package com.example.stream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.springframework.batch.infrastructure.item.ExecutionContext;
import org.springframework.batch.infrastructure.item.ItemStreamException;
import org.springframework.batch.infrastructure.item.ItemStreamReader;
import org.springframework.batch.infrastructure.item.NonTransientResourceException;
import org.springframework.batch.infrastructure.item.ParseException;
import org.springframework.batch.infrastructure.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class FileStreamReader implements ItemStreamReader<String> {

	private final Resource resource;

	private BufferedReader reader;

	public FileStreamReader(@Value("classpath:/input.txt") final Resource resource) {
		this.resource = resource;
	}

	@Override
	public void open(final ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("*open*");
		try {
			reader = new BufferedReader(
					new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
		} catch (final IOException e) {
			throw new ItemStreamException(e);
		}
	}

	@Override
	public void update(final ExecutionContext executionContext) throws ItemStreamException {
		System.out.println("*update*");
	}

	@Override
	public void close() throws ItemStreamException {
		System.out.println("*close*");
		try {
			reader.close();
		} catch (final IOException e) {
			throw new ItemStreamException(e);
		}
	}

	@Override
	public String read() throws Exception, UnexpectedInputException, ParseException,
			NonTransientResourceException {
		return reader.readLine();
	}
}
