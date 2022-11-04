package com.example.stream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.example.chunk.PrintlnItemWriter;
import com.example.chunk.TwoRepeatItemProcessor;

@SpringBootApplication
@EnableBatchProcessing
@Import({ TwoRepeatItemProcessor.class, PrintlnItemWriter.class })
public class StreamExample {

	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;
	private final FileStreamReader reader;
	private final TwoRepeatItemProcessor processor;
	private final PrintlnItemWriter writer;

	public StreamExample(final JobBuilderFactory jobs, final StepBuilderFactory steps,
			final FileStreamReader reader, final TwoRepeatItemProcessor processor,
			final PrintlnItemWriter writer) {
		this.jobs = jobs;
		this.steps = steps;
		this.reader = reader;
		this.processor = processor;
		this.writer = writer;
	}

	@Bean
	public Job streamExampleJob() {
		return jobs.get("streamExampleJob").start(streamExampleStep()).build();
	}

	@Bean
	public Step streamExampleStep() {
		return steps.get("streamExampleStep")
				.<String, String> chunk(3).reader(reader).processor(processor).writer(writer)
				.build();
	}
}
