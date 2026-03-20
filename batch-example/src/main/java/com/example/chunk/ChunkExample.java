package com.example.chunk;

import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ChunkExample {

    private final JobRepository jobRepository;
    private final FooBarItemReader reader;
    private final TwoRepeatItemProcessor processor;
    private final PrintlnItemWriter writer;

    public ChunkExample(
            JobRepository jobRepository,
            final FooBarItemReader reader,
            final TwoRepeatItemProcessor processor,
            final PrintlnItemWriter writer) {
        this.jobRepository = jobRepository;
        this.reader = reader;
        this.processor = processor;
        this.writer = writer;
    }

    @Bean
    public Job chunkExampleJob() {
        return new JobBuilder("chunkExampleJob", jobRepository)
                .start(chunkExampleStep())
                .build();
    }

    @Bean
    public Step chunkExampleStep() {
        return new StepBuilder("chunkExampleStep", jobRepository)
                .<String, String>chunk(1)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
