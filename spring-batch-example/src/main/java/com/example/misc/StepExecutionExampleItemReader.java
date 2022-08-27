package com.example.misc;

import java.util.Iterator;
import java.util.stream.IntStream;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepExecutionExampleItemReader implements ItemReader<Integer> {

	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	private final Iterator<Integer> it = IntStream.rangeClosed(1, 10).boxed().iterator();

	@Override
	public Integer read() {

		log.info("{}", stepExecution);

		if (it.hasNext()) {
			return it.next();
		}

		return null;
	}
}
