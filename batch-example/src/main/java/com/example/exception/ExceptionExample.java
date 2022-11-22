package com.example.exception;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import com.example.chunk.PrintlnItemWriter;

@SpringBootApplication
@Import(PrintlnItemWriter.class)
public class ExceptionExample {

	private final JobBuilderFactory jobs;
	private final StepBuilderFactory steps;
	private final PrintlnItemWriter writer;
	private final ItemProcessor<Integer, String> processor = String::valueOf;

	public ExceptionExample(final JobBuilderFactory jobs, final StepBuilderFactory steps,
			final PrintlnItemWriter writer) {
		this.jobs = jobs;
		this.steps = steps;
		this.writer = writer;
	}

	//ParseException

	@Bean
	@Qualifier("job1")
	public Job exceptionExampleJob1() {
		return jobs.get("exceptionExampleJob1").start(exceptionExampleStep1()).build();
	}

	@Bean
	public Step exceptionExampleStep1() {
		return steps.get("exceptionExampleStep1")
				.<Integer, String> chunk(3).reader(reader1()).processor(processor).writer(writer)
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
		return jobs.get("exceptionExampleJob2").start(exceptionExampleStep2()).build();
	}

	@Bean
	public Step exceptionExampleStep2() {
		return steps.get("exceptionExampleStep2")
				.<Integer, String> chunk(3).reader(reader2()).processor(processor).writer(writer)
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
		return jobs.get("exceptionExampleJob3").start(exceptionExampleStep3()).build();
	}

	@Bean
	public Step exceptionExampleStep3() {
		return steps.get("exceptionExampleStep3")
				.<Integer, String> chunk(3).reader(reader3()).processor(processor).writer(writer)
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
		return jobs.get("exceptionExampleJob4").start(exceptionExampleStep4()).build();
	}

	@Bean
	public Step exceptionExampleStep4() {
		return steps.get("exceptionExampleStep4")
				.<Integer, String> chunk(3).reader(reader4()).processor(processor).writer(writer)
				.build();
	}

	@Bean
	public ExceptionThrownInTheMiddleStreamReader reader4() {
		return new ExceptionThrownInTheMiddleStreamReader(Exception::new);
	}
}
