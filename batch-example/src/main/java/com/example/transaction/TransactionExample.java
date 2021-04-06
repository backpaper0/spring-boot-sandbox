package com.example.transaction;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class TransactionExample {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final MetaVarReader reader;
    private final MetaVarWriter writer;
    private final PrintlnStepExecutionListener listener;

    public TransactionExample(final JobBuilderFactory jobs, final StepBuilderFactory steps,
            final MetaVarReader reader, final MetaVarWriter writer,
            final PrintlnStepExecutionListener listener) {
        this.jobs = jobs;
        this.steps = steps;
        this.reader = reader;
        this.writer = writer;
        this.listener = listener;
    }

    @Bean
    public Job transactionExampleJob() {
        return jobs.get("transactionExampleJob").start(transactionExampleStep()).build();
    }

    @Bean
    public Step transactionExampleStep() {
        return steps.get("transactionExampleStep").<String, String> chunk(5).reader(reader)
                .writer(writer).listener(listener).build();
    }
}
