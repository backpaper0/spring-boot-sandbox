package com.example.multithreaded;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class MultiThreadedExample {

    private final JobRepository jobRepository;

    public MultiThreadedExample(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Bean
    public Job multiThreadedJob() {
        return new JobBuilder(MultiThreadedExample.class.getSimpleName() + "Job", jobRepository)
                .start(multiThreadedStep())
                .build();
    }

    @Bean
    public Step multiThreadedStep() {
        return new StepBuilder(MultiThreadedExample.class.getSimpleName() + "Step", jobRepository)
                .<Integer, Integer>chunk(3)
                .reader(multiThreadedReader())
                .writer(multiThreadedWriter())
                .taskExecutor(multiThreadedTaskExecutor())
                .build();
    }

    @Bean
    public AsyncTaskExecutor multiThreadedTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(2);
        taskExecutor.setMaxPoolSize(2);
        return taskExecutor;
    }

    @Bean
    public ItemReader<Integer> multiThreadedReader() {
        List<Integer> list = IntStream.range(1, 20).boxed().collect(Collectors.toList());
        Iterator<Integer> it = Collections.synchronizedCollection(list).iterator();
        return () -> {
            System.out.printf("[%s] read%n", Thread.currentThread().getName());
            return it.hasNext() ? it.next() : null;
        };
    }

    @Bean
    public ItemWriter<Integer> multiThreadedWriter() {
        return items -> {
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.printf("[%s] write: %s%n", Thread.currentThread().getName(), items);
        };
    }
}
