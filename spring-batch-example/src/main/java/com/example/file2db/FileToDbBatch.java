package com.example.file2db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.example.common.ExitCodeGeneratorImpl;
import com.example.common.LoggingListener;
import com.example.common.Task;

@Configuration
public class FileToDbBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;
	private final DataSource dataSource;
	private final ExitCodeGeneratorImpl exitCodeGeneratorImpl;
	private final LoggingListener loggingListener;
	private final BeanValidatingItemProcessor<?> beanValidatingItemProcessor;

	public FileToDbBatch(StepBuilderFactory steps, JobBuilderFactory jobs, DataSource dataSource,
			ExitCodeGeneratorImpl exitCodeGeneratorImpl, LoggingListener loggingListener,
			BeanValidatingItemProcessor<?> beanValidatingItemProcessor) {
		this.steps = steps;
		this.jobs = jobs;
		this.dataSource = dataSource;
		this.exitCodeGeneratorImpl = exitCodeGeneratorImpl;
		this.loggingListener = loggingListener;
		this.beanValidatingItemProcessor = beanValidatingItemProcessor;
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Task> fileToDbItemReader(
			@Value("#{jobParameters['input.file'] ?: 'inputs/input-invalid.csv'}") String file) {
		return new FlatFileItemReaderBuilder<Task>()
				.resource(new PathResource(file))
				.encoding("UTF-8")
				.linesToSkip(1)
				.targetType(Task.class)
				.delimited().names("id", "content", "done")
				.saveState(false)
				.build();
	}

	@Bean
	public CompositeItemProcessor<Task, Task> fileToDbItemProcessor() {
		CompositeItemProcessor<Task, Task> processor = new CompositeItemProcessor<>();
		processor.setDelegates(List.of(beanValidatingItemProcessor));
		return processor;
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Task> fileToDbItemWriter() {
		return new JdbcBatchItemWriterBuilder<Task>()
				.dataSource(dataSource)
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Task>())
				.sql("insert into tasks (id, content, done) values (:id, :content, :done)")
				.build();
	}

	@Bean
	public Step fileToDbStep() {
		return steps.get("FileToDb")
				.<Task, Task> chunk(2)

				.reader(fileToDbItemReader(null))
				.processor(fileToDbItemProcessor())
				.writer(fileToDbItemWriter())

				.faultTolerant()
				.skip(FlatFileParseException.class)
				.skip(ValidationException.class)
				.skipLimit(10)

				.listener(loggingListener)
				.listener(exitCodeGeneratorImpl)
				.build();
	}

	@Bean
	public Job fileToDbJob() {
		return jobs.get("FileToDb")
				.start(fileToDbStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
