package com.example;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.sql.DataSource;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@EnableBatchProcessing
public class TwoDatasourceBatchSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwoDatasourceBatchSampleApplication.class, args);
    }

    @Autowired
    JobBuilderFactory jobs;
    @Autowired
    StepBuilderFactory steps;

    @Bean
    Job job() {
        return jobs.get("helloJob").start(step()).build();
    }

    @Bean
    Step step() {
        return steps.get("helloStep").tasklet(tasklet()).build();
    }

    @Bean
    Tasklet tasklet() {
        return (a, b) -> {
            System.out.println("Hello, Spring Batch!!!");
            return RepeatStatus.FINISHED;
        };
    }

    //2つ以上DataSourceが定義されているとBatchConfigurerも自前で定義しないといけない。
    //DefaultBatchConfigurerを使えばDataSourceを渡すだけなので楽。
    @Bean
    BatchConfigurer batchConfigurer() {
        return new DefaultBatchConfigurer(dataSource());
    }

    //DataSource 1

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "my-datasource.primary")
    DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    DataSource dataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    //DataSource 2

    @Bean
    @Secondary
    @ConfigurationProperties(prefix = "my-datasource.primary")
    DataSourceProperties secondaryDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Secondary
    DataSource secondaryDataSource() {
        return dataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Qualifier
    @interface Secondary {
    }
}
