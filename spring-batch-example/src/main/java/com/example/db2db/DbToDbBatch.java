package com.example.db2db;

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
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.transaction.PlatformTransactionManager;

import com.example.common.ExitCodeGeneratorImpl;
import com.example.common.LoggingListener;

@Configuration
public class DbToDbBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	private final DataSource dataSource;
	private final ExitCodeGeneratorImpl exitCodeGeneratorImpl;
	private final LoggingListener loggingListener;
	private final Demo2UpdatingItemProcessor dbToDbItemProcessor;

	public DbToDbBatch(DataSource dataSource,
			ExitCodeGeneratorImpl exitCodeGeneratorImpl, LoggingListener loggingListener,
			Demo2UpdatingItemProcessor dbToDbItemProcessor) {
		this.dataSource = dataSource;
		this.exitCodeGeneratorImpl = exitCodeGeneratorImpl;
		this.loggingListener = loggingListener;
		this.dbToDbItemProcessor = dbToDbItemProcessor;
	}

	@Bean
	@StepScope
	public JdbcCursorItemReader<Integer> dbToDbItemReader() {
		return new JdbcCursorItemReaderBuilder<Integer>()
				.dataSource(dataSource)
				.sql("select id from demo2 where status = 'INIT'")
				.rowMapper(new SingleColumnRowMapper<>(Integer.class))
				.saveState(false)
				.build();
	}

	@Bean
	@StepScope
	public JdbcBatchItemWriter<Demo2> dbToDbItemWriter() {
		return new JdbcBatchItemWriterBuilder<Demo2>()
				.dataSource(dataSource)
				.itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Demo2>())
				.sql("update demo2 set status = :status where id = :id")
				.build();
	}

	@Bean
	public Step dbToDbStep() {
		return new StepBuilder("DbToDb", jobRepository)
				.<Integer, Demo2> chunk(7, transactionManager)

				.reader(dbToDbItemReader())
				.processor(dbToDbItemProcessor)
				.writer(dbToDbItemWriter())

				.faultTolerant()
				.skip(FlatFileParseException.class)
				.skip(ValidationException.class)
				.skipLimit(10)

				.listener(loggingListener)
				.listener(exitCodeGeneratorImpl)
				.build();
	}

	@Bean
	public Job dbToDbJob() {
		return new JobBuilder("DbToDb", jobRepository)
				.start(dbToDbStep())
				.incrementer(new RunIdIncrementer())
				.build();
	}
}
