package com.example.chunk;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class ChunkExample {

	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;
	private final FooBarItemReader reader;
	private final TwoRepeatItemProcessor processor;
	private final PrintlnItemWriter writer;

	public ChunkExample(final JobBuilderFactory jobs, final StepBuilderFactory steps,
			final FooBarItemReader reader, final TwoRepeatItemProcessor processor,
			final PrintlnItemWriter writer) {
		this.jobs = jobs;
		this.steps = steps;
		this.reader = reader;
		this.processor = processor;
		this.writer = writer;
	}

	@Bean
	public Job chunkExampleJob() {
		return jobs.get("chunkExampleJob").start(chunkExampleStep()).build();
	}

	@Bean
	public Step chunkExampleStep() {
		return steps.get("chunkExampleStep")
				.<String, String> chunk(1).reader(reader).processor(processor).writer(writer)
				.build();
	}
}
