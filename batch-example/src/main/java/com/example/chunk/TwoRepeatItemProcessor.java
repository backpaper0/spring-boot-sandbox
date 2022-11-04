package com.example.chunk;

import java.util.StringJoiner;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TwoRepeatItemProcessor implements ItemProcessor<String, String> {

	@Override
	public String process(final String item) throws Exception {
		return new StringJoiner("*").add(item).add(item).toString();
	}
}
