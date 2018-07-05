package com.example;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobs;
    private final StepBuilderFactory steps;
    private final HelloTasklet tasklet;
    private final DataSource dataSource;

    public BatchConfig(final JobBuilderFactory jobs, final StepBuilderFactory steps,
            final HelloTasklet tasklet,
            final DataSource dataSource) {
        this.jobs = jobs;
        this.steps = steps;
        this.tasklet = tasklet;
        this.dataSource = dataSource;
    }

    @Bean
    public Job job() {
        return jobs.get("helloJob").start(step()).build();
    }

    @Bean
    public Step step() {
        return steps.get("helloStep").tasklet(tasklet).build();
    }

    //2つ以上DataSourceが定義されているとBatchConfigurerも自前で定義しないといけない。
    //DefaultBatchConfigurerを使えばDataSourceを渡すだけなので楽。
    @Bean
    public BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer(dataSource);
    }
}
