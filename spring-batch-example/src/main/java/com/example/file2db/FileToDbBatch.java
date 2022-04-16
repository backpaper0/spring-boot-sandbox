package com.example.file2db;

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
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
public class FileToDbBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;
	private final DataSource dataSource;
	private final LocalValidatorFactoryBean localValidatorFactoryBean;

	public FileToDbBatch(StepBuilderFactory steps, JobBuilderFactory jobs, DataSource dataSource,
			LocalValidatorFactoryBean localValidatorFactoryBean) {
		this.steps = steps;
		this.jobs = jobs;
		this.dataSource = dataSource;
		this.localValidatorFactoryBean = localValidatorFactoryBean;
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Task> tasksFileReader(
			@Value("#{jobParameters['input.file'] ?: 'inputs/input.csv'}") String file) {
		return new FlatFileItemReaderBuilder<Task>()
				.name("TasksFileReader")
				.resource(new PathResource(file))
				.encoding("UTF-8")
				.linesToSkip(1)
				.targetType(Task.class)
				.delimited().names("id", "content", "done")
				.build();
	}

	@Bean
	public BeanValidatingItemProcessor<Task> beanValidatingItemProcessor() {
		return new BeanValidatingItemProcessor<>(localValidatorFactoryBean);
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Task> tasksDbWriter() {
		return new JdbcBatchItemWriterBuilder<Task>()
				.dataSource(dataSource)
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Task>())
				.sql("insert into tasks (id, content, done) values (:id, :content, :done)")
				.build();
	}

	@Bean
	@StepScope
	public WarningLoggingListener<Task, Task> skipListener() {
		return new WarningLoggingListener<>();
	}

	@Bean
	public ExitCodeGeneratorImpl<Task, Task> exitCodeGeneratorImpl() {
		return new ExitCodeGeneratorImpl<>();
	}

	@Bean
	public Step fileToDbStep() {
		return steps.get("FileToDb")
				.<Task, Task> chunk(1)
				.reader(tasksFileReader(null))
				.processor(beanValidatingItemProcessor())
				.writer(tasksDbWriter())
				.faultTolerant()
				.skip(FlatFileParseException.class)
				.skip(ValidationException.class)
				.skipLimit(10)
				.listener(skipListener())
				.listener(exitCodeGeneratorImpl())
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
