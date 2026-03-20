package com.example.transaction;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TransactionExample {

    private final JobRepository jobRepository;
    private final MetaVarReader reader;
    private final MetaVarWriter writer;
    private final PrintlnStepExecutionListener listener;

    public TransactionExample(
            JobRepository jobRepository,
            final MetaVarReader reader,
            final MetaVarWriter writer,
            final PrintlnStepExecutionListener listener) {
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.writer = writer;
        this.listener = listener;
    }

    @Bean
    public Job transactionExampleJob() {
        return new JobBuilder("transactionExampleJob", jobRepository)
                .start(transactionExampleStep())
                .build();
    }

    @Bean
    public Step transactionExampleStep() {
        return new StepBuilder("transactionExampleStep", jobRepository)
                .<String, String>chunk(5)
                .reader(reader)
                .writer(writer)
                .listener(listener)
                .build();
    }
}
