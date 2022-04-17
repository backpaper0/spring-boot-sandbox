package com.example.chunk;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChunkDemoBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;

	public ChunkDemoBatch(StepBuilderFactory steps, JobBuilderFactory jobs) {
		this.steps = steps;
		this.jobs = jobs;
	}

	@Bean
	@StepScope
	public ChunkDemoItemReader chunkDemoItemReader() {
		return new ChunkDemoItemReader();
	}

	@Bean
	@StepScope
	public ChunkDemoItemProcessor chunkDemoItemProcessor() {
		return new ChunkDemoItemProcessor();
	}

	@Bean
	@StepScope
	public ChunkDemoItemWriter chunkDemoItemWriter() {
		return new ChunkDemoItemWriter();
	}

	@Bean
	public Step chunkDemoStep() {
		return steps.get("ChunkDemo")
				.<Integer, Integer> chunk(3)
				.reader(chunkDemoItemReader())
				.processor(chunkDemoItemProcessor())
				.writer(chunkDemoItemWriter())
				.build();
	}

	@Bean
	public Job chunkDemoJob() {
		return jobs.get("ChunkDemo")
				.start(chunkDemoStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}

	static class ChunkDemoItemReader implements ItemStreamReader<Integer> {

		private static final Logger logger = LoggerFactory.getLogger(ChunkDemoItemReader.class);
		private final AtomicInteger values = new AtomicInteger();
		private final int size = 5;

		@Override
		public void open(ExecutionContext executionContext) {
			logger.info("reader#open");
		}

		@Override
		public void update(ExecutionContext executionContext) {
			logger.info("reader#update");
		}

		@Override
		public void close() {
			logger.info("reader#close");
		}

		@Override
		public Integer read() {
			Integer value = values.get() < size ? values.incrementAndGet() : null;
			logger.info("reader#read: {}", value);
			return value;
		}
	}

	static class ChunkDemoItemProcessor implements ItemProcessor<Integer, Integer> {

		private static final Logger logger = LoggerFactory.getLogger(ChunkDemoItemProcessor.class);

		@Override
		public Integer process(Integer item) throws Exception {
			logger.info("processor#process: {}", item);
			return item;
		}
	}

	static class ChunkDemoItemWriter implements ItemStreamWriter<Integer> {

		private static final Logger logger = LoggerFactory.getLogger(ChunkDemoItemWriter.class);

		@Override
		public void open(ExecutionContext executionContext) throws ItemStreamException {
			logger.info("writer#open");
		}

		@Override
		public void update(ExecutionContext executionContext) throws ItemStreamException {
			logger.info("writer#update");
		}

		@Override
		public void close() throws ItemStreamException {
			logger.info("writer#close");
		}

		@Override
		public void write(List<? extends Integer> items) throws Exception {
			logger.info("writer#write: {}", items);
		}
	}
}
