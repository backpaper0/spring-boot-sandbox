package com.example.chunk;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.misc.DemoItemProcessor;
import com.example.misc.DemoItemReader;
import com.example.misc.DemoItemWriter;

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
	public DemoItemReader chunkDemoItemReader() {
		return new DemoItemReader(5);
	}

	@Bean
	@StepScope
	public DemoItemProcessor chunkDemoItemProcessor() {
		return new DemoItemProcessor();
	}

	@Bean
	@StepScope
	public DemoItemWriter chunkDemoItemWriter() {
		return new DemoItemWriter();
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
}
