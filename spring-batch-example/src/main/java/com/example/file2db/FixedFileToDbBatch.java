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
import org.springframework.batch.item.file.transform.DefaultFieldSetFactory;
import org.springframework.batch.item.file.transform.FieldSetFactory;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.example.common.ExitCodeGeneratorImpl;
import com.example.common.LoggingListener;
import com.example.file2db.TrimmingFieldSetFactory.TrimmingType;

@Configuration
public class FixedFileToDbBatch {

	private final StepBuilderFactory steps;
	private final JobBuilderFactory jobs;
	private final DataSource dataSource;
	private final ExitCodeGeneratorImpl exitCodeGeneratorImpl;
	private final LoggingListener loggingListener;
	private final BeanValidatingItemProcessor<?> beanValidatingItemProcessor;

	public FixedFileToDbBatch(StepBuilderFactory steps, JobBuilderFactory jobs, DataSource dataSource,
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
	public FlatFileItemReader<Demo1> fixedFileToDbItemReader(
			@Value("#{jobParameters['input.file'] ?: 'inputs/input-fixed.txt'}") String file) {
		FieldSetFactory fieldSetFactory = new TrimmingFieldSetFactory(
				new DefaultFieldSetFactory(),
				TrimmingType.PREFIX_ZERO,
				TrimmingType.SUFFIX_SPACE);
		return new FlatFileItemReaderBuilder<Demo1>()
				.resource(new PathResource(file))
				.encoding("UTF-8")
				.linesToSkip(1)
				.targetType(Demo1.class)
				.fixedLength()
				.addColumns(new Range(1, 4))
				.addColumns(new Range(5, 14))
				.fieldSetFactory(fieldSetFactory)
				.names("id", "content")
				.saveState(false)
				.build();
	}

	@Bean
	public CompositeItemProcessor<Demo1, Demo1> fixedFileToDbItemProcessor() {
		CompositeItemProcessor<Demo1, Demo1> processor = new CompositeItemProcessor<>();
		processor.setDelegates(List.of(beanValidatingItemProcessor));
		return processor;
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Demo1> fixedFileToDbItemWriter() {
		return new JdbcBatchItemWriterBuilder<Demo1>()
				.dataSource(dataSource)
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Demo1>())
				.sql("insert into demo1 (id, content) values (:id, :content)")
				.build();
	}

	@Bean
	public Step fixedFileToDbStep() {
		return steps.get("FixedFileToDb")
				.<Demo1, Demo1> chunk(2)

				.reader(fixedFileToDbItemReader(null))
				.processor(fixedFileToDbItemProcessor())
				.writer(fixedFileToDbItemWriter())

				.faultTolerant()
				.skip(FlatFileParseException.class)
				.skip(ValidationException.class)
				.skipLimit(10)

				.listener(loggingListener)
				.listener(exitCodeGeneratorImpl)
				.build();
	}

	@Bean
	public Job fixedFileToDbJob() {
		return jobs.get("FixedFileToDb")
				.start(fixedFileToDbStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
