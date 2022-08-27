package com.example.misc;

import java.util.List;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepExecutionExampleItemWriter implements ItemWriter<Integer> {

	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	@Override
	public void write(List<? extends Integer> items) throws Exception {
		log.info("{}", stepExecution);
		log.info("{}", items);
	}
}
