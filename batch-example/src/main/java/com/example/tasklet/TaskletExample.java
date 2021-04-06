package com.example.tasklet;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableBatchProcessing
public class TaskletExample {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final HelloTasklet tasklet;

    public TaskletExample(final JobBuilderFactory jobs, final StepBuilderFactory steps,
            final HelloTasklet tasklet) {
        this.jobs = jobs;
        this.steps = steps;
        this.tasklet = tasklet;
    }

    @Bean
    public Job taskletExampleJob() {
        return jobs.get("taskletExampleJob").start(taskletExampleStep()).build();
    }

    @Bean
    public Step taskletExampleStep() {
        return steps.get("taskletExampleStep").tasklet(tasklet).build();
    }
}
