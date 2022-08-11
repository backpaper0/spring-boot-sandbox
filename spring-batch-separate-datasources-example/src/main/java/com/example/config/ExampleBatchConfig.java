package com.example.config;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SingleColumnRowMapper;

import com.example.entity.Example;
import com.example.processor.ExampleItemProcessor;

import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "example")
public class ExampleBatchConfig {

	@Autowired
	private StepBuilderFactory steps;
	@Autowired
	private JobBuilderFactory jobs;
	@Autowired
	private DataSource dataSource;
	@Autowired
	private ExampleItemProcessor exampleItemProcessor;
	@Setter
	private int chunkSize;

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
		return steps.get("example")
				.<Integer, Example> chunk(chunkSize)
				.reader(exampleItemReader())
				.processor(exampleItemProcessor)
				.writer(exampleItemWriter())
				.build();
	}

	@Bean
	public Job exampleJob() {
		return jobs.get("example")
				.start(exampleStep())
				.build();
	}
}
