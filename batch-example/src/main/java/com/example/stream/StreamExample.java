package com.example.stream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.chunk.PrintlnItemWriter;
import com.example.chunk.TwoRepeatItemProcessor;

@SpringBootApplication
@Import({ TwoRepeatItemProcessor.class, PrintlnItemWriter.class })
public class StreamExample {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final FileStreamReader reader;
	private final TwoRepeatItemProcessor processor;
	private final PrintlnItemWriter writer;

	public StreamExample(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			final FileStreamReader reader, final TwoRepeatItemProcessor processor,
			final PrintlnItemWriter writer) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.reader = reader;
		this.processor = processor;
		this.writer = writer;
	}

	@Bean
	public Job streamExampleJob() {
		return new JobBuilder("streamExampleJob", jobRepository).start(streamExampleStep()).build();
	}

	@Bean
	public Step streamExampleStep() {
		return new StepBuilder("streamExampleStep", jobRepository)
				.<String, String> chunk(3, transactionManager)
				.reader(reader).processor(processor).writer(writer).build();
	}
}
