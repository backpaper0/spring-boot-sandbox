package com.example.file2db;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.common.ExitCodeGeneratorImpl;
import com.example.common.LoggingListener;

@Configuration
public class FileToDbBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	private final DataSource dataSource;
	private final ExitCodeGeneratorImpl exitCodeGeneratorImpl;
	private final LoggingListener loggingListener;
	private final BeanValidatingItemProcessor<?> beanValidatingItemProcessor;

	public FileToDbBatch(DataSource dataSource,
			ExitCodeGeneratorImpl exitCodeGeneratorImpl, LoggingListener loggingListener,
			BeanValidatingItemProcessor<?> beanValidatingItemProcessor) {
		this.dataSource = dataSource;
		this.exitCodeGeneratorImpl = exitCodeGeneratorImpl;
		this.loggingListener = loggingListener;
		this.beanValidatingItemProcessor = beanValidatingItemProcessor;
	}

	@Bean
	@StepScope
	public FlatFileItemReader<Demo1> fileToDbItemReader(
			@Value("#{jobParameters['input.file'] ?: 'inputs/input-invalid.csv'}") String file) {
		return new FlatFileItemReaderBuilder<Demo1>()
				.resource(new PathResource(file))
				.encoding("UTF-8")
				.linesToSkip(1)
				.targetType(Demo1.class)
				.delimited().names("id", "content")
				.saveState(false)
				.build();
	}

	@Bean
	public CompositeItemProcessor<Demo1, Demo1> fileToDbItemProcessor() {
		CompositeItemProcessor<Demo1, Demo1> processor = new CompositeItemProcessor<>();
		processor.setDelegates(List.of(beanValidatingItemProcessor));
		return processor;
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Demo1> fileToDbItemWriter() {
		return new JdbcBatchItemWriterBuilder<Demo1>()
				.dataSource(dataSource)
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Demo1>())
				.sql("insert into demo1 (id, content) values (:id, :content)")
				.build();
	}

	@Bean
	public Step fileToDbStep() {
		return new StepBuilder("FileToDb", jobRepository)
				.<Demo1, Demo1> chunk(2, transactionManager)

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
		return new JobBuilder("FileToDb", jobRepository)
				.start(fileToDbStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
