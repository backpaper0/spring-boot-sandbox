package com.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.tasklet.ExampleTasklet;

@Configuration
@EnableBatchProcessing
@ComponentScan(basePackages = "com.example.tasklet")
public class ExampleConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final ExampleTasklet tasklet;

    public ExampleConfig(final JobBuilderFactory jobs, final StepBuilderFactory steps,
            final ExampleTasklet tasklet) {
        this.jobs = jobs;
        this.steps = steps;
        this.tasklet = tasklet;
    }

    @Bean(name = "exampleJob")
    public Job job() {
        return jobs.get("exampleJob").start(step()).build();
    }

    @Bean
    public Step step() {
        return steps.get("exampleStep").tasklet(tasklet).build();
    }
}
