package com.example.chunk;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.misc.DemoItemProcessor;
import com.example.misc.DemoItemReader;
import com.example.misc.DemoItemWriter;

@Configuration
public class ChunkDemoBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

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
		return new StepBuilder("ChunkDemo", jobRepository)
				.<Integer, Integer> chunk(3, transactionManager)
				.reader(chunkDemoItemReader())
				.processor(chunkDemoItemProcessor())
				.writer(chunkDemoItemWriter())
				.build();
	}

	@Bean
	public Job chunkDemoJob() {
		return new JobBuilder("ChunkDemo", jobRepository)
				.start(chunkDemoStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
