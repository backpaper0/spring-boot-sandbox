package com.example.db2file;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.file.transform.FieldExtractor;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import com.example.common.ExitCodeGeneratorImpl;
import com.example.common.LoggingListener;

@Configuration
public class DbToFileBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;
	private final DataSource dataSource;
	private final ExitCodeGeneratorImpl exitCodeGeneratorImpl;
	private final LoggingListener loggingListener;

	public DbToFileBatch(StepBuilderFactory steps, JobBuilderFactory jobs, DataSource dataSource,
			ExitCodeGeneratorImpl exitCodeGeneratorImpl, LoggingListener loggingListener) {
		this.steps = steps;
		this.jobs = jobs;
		this.dataSource = dataSource;
		this.exitCodeGeneratorImpl = exitCodeGeneratorImpl;
		this.loggingListener = loggingListener;
	}

	@Bean
	@StepScope
	public JdbcCursorItemReader<Demo3> dbToFileItemReader() {
		return new JdbcCursorItemReaderBuilder<Demo3>()
				.dataSource(dataSource)
				.sql("select id, content1, content2 from demo3 order by id asc")
				.rowMapper(new BeanPropertyRowMapper<>(Demo3.class))
				.saveState(false)
				.build();
	}

	@Bean
	public PassThroughItemProcessor<Demo3> dbToFileItemProcessor() {
		return new PassThroughItemProcessor<>();
	}

	@Bean
	@StepScope
	public FlatFileItemWriter<Demo3> dbToFileItemWriter(
			@Value("#{jobParameters['output.file'] ?: 'target/output.txt'}") String file) {
		FieldExtractor<Demo3> fieldExtractor = item -> new Object[] {
				item.getId(),
				item.getContent1(),
				FullWidthUtil.fillPad(item.getContent2(), 20)
		};
		return new FlatFileItemWriterBuilder<Demo3>()
				.resource(new FileSystemResource(file))
				.formatted()
				.format("%04d%-10s%s")
				.fieldExtractor(fieldExtractor)
				.saveState(false)
				.name("DbToFile")
				.build();
	}

	@Bean
	public Step dbToFileStep() {
		return steps.get("DbToFile")
				.<Demo3, Demo3> chunk(2)

				.reader(dbToFileItemReader())
				.processor(dbToFileItemProcessor())
				.writer(dbToFileItemWriter(null))

				.faultTolerant()
				.skip(FlatFileParseException.class)
				.skip(ValidationException.class)
				.skipLimit(10)

				.listener(loggingListener)
				.listener(exitCodeGeneratorImpl)
				.build();
	}

	@Bean
	public Job dbToFileJob() {
		return jobs.get("DbToFile")
				.start(dbToFileStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
