package com.example.file2db;

import java.util.ArrayList;
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
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.batch.item.validator.BeanValidatingItemProcessor;
import org.springframework.batch.item.validator.ValidationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.PathResource;

import com.example.common.ExitCodeGeneratorImpl;
import com.example.common.LoggingListener;
import com.example.file2db.FixedByteLengthLineTokenizer.Column;
import com.example.file2db.FixedByteLengthLineTokenizer.DataType;

@Configuration
public class FixedByBytesFileToDbBatch {

    private final StepBuilderFactory steps;
    private final JobBuilderFactory jobs;
    private final DataSource dataSource;
    private final ExitCodeGeneratorImpl exitCodeGeneratorImpl;
    private final LoggingListener loggingListener;
    private final BeanValidatingItemProcessor<?> beanValidatingItemProcessor;

    public FixedByBytesFileToDbBatch(StepBuilderFactory steps, JobBuilderFactory jobs, DataSource dataSource,
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
    public FlatFileItemReader<Demo1> fixedByBytesFileToDbItemReader(
            @Value("#{jobParameters['input.file'] ?: 'inputs/input-fixed-sjis.txt'}") String file) {

        List<Column> columns = new ArrayList<>();
        columns.add(new Column("content", 20, DataType.STRING));
        columns.add(new Column("id", 4, DataType.NUMBER));
        LineTokenizer tokenizer = new FixedByteLengthLineTokenizer(columns, FixedByteLengthLineTokenizer.SHIFT_JIS);

        return new FlatFileItemReaderBuilder<Demo1>()
                .resource(new PathResource(file))
                .encoding("Windows-31J")
                .linesToSkip(1)
                .targetType(Demo1.class)
                .lineTokenizer(tokenizer)
                .saveState(false)
                .build();
    }

    @Bean
    public CompositeItemProcessor<Demo1, Demo1> fixedByBytesFileToDbItemProcessor() {
        CompositeItemProcessor<Demo1, Demo1> processor = new CompositeItemProcessor<>();
        processor.setDelegates(List.of(beanValidatingItemProcessor));
        return processor;
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<Demo1> fixedByBytesFileToDbItemWriter() {
        return new JdbcBatchItemWriterBuilder<Demo1>()
                .dataSource(dataSource)
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Demo1>())
                .sql("insert into demo1 (id, content) values (:id, :content)")
                .build();
    }

    @Bean
    public Step fixedByBytesFileToDbStep() {
        return steps.get("FixedByBytesFileToDb")
                .<Demo1, Demo1> chunk(2)

                .reader(fixedByBytesFileToDbItemReader(null))
                .processor(fixedByBytesFileToDbItemProcessor())
                .writer(fixedByBytesFileToDbItemWriter())

                .faultTolerant()
                .skip(FlatFileParseException.class)
                .skip(ValidationException.class)
                .skipLimit(10)

                .listener(loggingListener)
                .listener(exitCodeGeneratorImpl)
                .build();
    }

    @Bean
    public Job fixedByBytesFileToDbJob() {
        return jobs.get("FixedByBytesFileToDb")
                .start(fixedByBytesFileToDbStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
