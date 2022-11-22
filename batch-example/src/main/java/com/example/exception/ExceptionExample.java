package com.example.exception;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.chunk.PrintlnItemWriter;

@SpringBootApplication
@Import(PrintlnItemWriter.class)
public class ExceptionExample {

	private final JobRepository jobRepository;
	private final PlatformTransactionManager transactionManager;
	private final PrintlnItemWriter writer;
	private final ItemProcessor<Integer, String> processor = String::valueOf;

	public ExceptionExample(JobRepository jobRepository, PlatformTransactionManager transactionManager,
			final PrintlnItemWriter writer) {
		this.jobRepository = jobRepository;
		this.transactionManager = transactionManager;
		this.writer = writer;
	}

	//ParseException

	@Bean
	@Qualifier("job1")
	public Job exceptionExampleJob1() {
		return new JobBuilder("exceptionExampleJob1", jobRepository).start(exceptionExampleStep1()).build();
	}

	@Bean
	public Step exceptionExampleStep1() {
		return new StepBuilder("exceptionExampleStep1", jobRepository)
				.<Integer, String> chunk(3, transactionManager)
				.reader(reader1()).processor(processor).writer(writer)
				.build();
	}

	@Bean
	public ExceptionThrownInTheMiddleStreamReader reader1() {
		return new ExceptionThrownInTheMiddleStreamReader(ParseException::new);
	}

	// UnexpectedInputException

	@Bean
	@Qualifier("job2")
	public Job exceptionExampleJob2() {
		return new JobBuilder("exceptionExampleJob2", jobRepository).start(exceptionExampleStep2()).build();
	}

	@Bean
	public Step exceptionExampleStep2() {
		return new StepBuilder("exceptionExampleStep2", jobRepository)
				.<Integer, String> chunk(3, transactionManager)
				.reader(reader2()).processor(processor).writer(writer)
				.build();
	}

	@Bean
	public ExceptionThrownInTheMiddleStreamReader reader2() {
		return new ExceptionThrownInTheMiddleStreamReader(UnexpectedInputException::new);
	}

	// NonTransientResourceException

	@Bean
	@Qualifier("job3")
	public Job exceptionExampleJob3() {
		return new JobBuilder("exceptionExampleJob3", jobRepository).start(exceptionExampleStep3()).build();
	}

	@Bean
	public Step exceptionExampleStep3() {
		return new StepBuilder("exceptionExampleStep3", jobRepository)
				.<Integer, String> chunk(3, transactionManager)
				.reader(reader3()).processor(processor).writer(writer)
				.build();
	}

	@Bean
	public ExceptionThrownInTheMiddleStreamReader reader3() {
		return new ExceptionThrownInTheMiddleStreamReader(NonTransientResourceException::new);
	}

	// Exception

	@Bean
	@Qualifier("job4")
	public Job exceptionExampleJob4() {
		return new JobBuilder("exceptionExampleJob4", jobRepository).start(exceptionExampleStep4()).build();
	}

	@Bean
	public Step exceptionExampleStep4() {
		return new StepBuilder("exceptionExampleStep4", jobRepository)
				.<Integer, String> chunk(3, transactionManager)
				.reader(reader4()).processor(processor).writer(writer)
				.build();
	}

	@Bean
	public ExceptionThrownInTheMiddleStreamReader reader4() {
		return new ExceptionThrownInTheMiddleStreamReader(Exception::new);
	}
}
