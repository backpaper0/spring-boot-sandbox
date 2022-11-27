package com.example.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.entity.Example;
import com.example.processor.ExampleItemProcessor;

@Configuration
public class ExampleBatchConfig {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	@AppDataSource
	private PlatformTransactionManager transactionManager;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ExampleItemProcessor exampleItemProcessor;
	@Autowired
	private ExampleBatchProperties properties;

	@Bean
	@StepScope
	public JdbcCursorItemReader<Integer> exampleItemReader() {
		return new JdbcCursorItemReaderBuilder<Integer>()
				.dataSource(dataSource)
				.sql("select id from example order by id asc")
				.rowMapper(SingleColumnRowMapper.newInstance(Integer.class))
				.saveState(false)
				.build();
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Example> exampleItemWriter() {
		return new JdbcBatchItemWriterBuilder<Example>()
				.dataSource(dataSource)
				.sql("update example set content = :content where id = :id")
				.beanMapped()
				.build();
	}

	@Bean
	public Step exampleStep() {
		return new StepBuilder("example", jobRepository)
				.<Integer, Example> chunk(properties.getChunkSize(), transactionManager)
				.reader(exampleItemReader())
				.processor(exampleItemProcessor)
				.writer(exampleItemWriter())
				.build();
	}

	@Bean
	public Job exampleJob() {
		return new JobBuilder("example", jobRepository)
				.start(exampleStep())
				.build();
	}
}
