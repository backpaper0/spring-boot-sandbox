package com.example.misc;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StepExecutionExampleItemProcessor implements ItemProcessor<Integer, Integer> {

	@Value("#{stepExecution}")
	private StepExecution stepExecution;

	@Override
	public Integer process(Integer item) {
		log.info("{}", stepExecution);
		return item;
	}
}
