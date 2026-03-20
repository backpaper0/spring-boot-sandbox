package com.example.multithread;

import com.example.misc.DemoItemProcessor;
import com.example.misc.DemoItemReader;
import com.example.misc.DemoItemWriter;
import com.example.misc.SleepItemProcessor;
import com.example.misc.SleepItemReader;
import com.example.misc.SleepItemWriter;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MultiThreadDemoBatch {

    @Autowired
    private JobRepository jobRepository;

    @Bean
    @StepScope
    public DemoItemReader multiThreadDemoItemReader() {
        return new DemoItemReader(5);
    }

    @Bean
    @StepScope
    public DemoItemProcessor multiThreadDemoItemProcessor() {
        return new DemoItemProcessor();
    }

    @Bean
    @StepScope
    public DemoItemWriter multiThreadDemoItemWriter() {
        return new DemoItemWriter();
    }

    @Bean
    public AsyncTaskExecutor multiThreadDemoTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(Runtime.getRuntime().availableProcessors() - 1);
        return taskExecutor;
    }

    @Bean
    public Step multiThreadDemoStep() {
        return new StepBuilder("MultiThreadDemo", jobRepository)
                .<Integer, Integer>chunk(3)
                .reader(new SleepItemReader<>(multiThreadDemoItemReader(), 1000))
                .processor(new SleepItemProcessor<>(multiThreadDemoItemProcessor(), 1000))
                .writer(new SleepItemWriter<>(multiThreadDemoItemWriter(), 1000))
                .taskExecutor(multiThreadDemoTaskExecutor())
                .build();
    }

    @Bean
    public Job multiThreadDemoJob() {
        return new JobBuilder("MultiThreadDemo", jobRepository)
                .start(multiThreadDemoStep())
                .incrementer(new RunIdIncrementer())
                .build();
    }
}
