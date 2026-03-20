package com.example.misc;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.support.CompositeItemWriter;
import org.springframework.batch.infrastructure.item.support.ListItemReader;
import org.springframework.batch.infrastructure.item.support.ListItemWriter;
import org.springframework.batch.infrastructure.item.support.PassThroughItemProcessor;
import org.springframework.batch.infrastructure.item.support.builder.CompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * {@link CompositeItemWriter}を使って複数の{@link ItemWriter}をまとめる例。
 *
 */
@SpringBootTest
public class CompositeItemWriterTest {

    @Autowired
    JobOperator jobOperator;

    @Autowired
    TestConfig config;

    @Test
    void test() throws Exception {
        jobOperator.run(config.job(), new JobParameters());
        List<Integer> expected = IntStream.rangeClosed(1, 10).boxed().toList();
        assertEquals(expected, config.itemWriter1().getWrittenItems());
        assertEquals(expected, config.itemWriter2().getWrittenItems());
    }

    @TestConfiguration
    static class TestConfig {

        @Autowired
        private JobRepository jobRepository;

        @Bean
        public ListItemReader<Integer> itemReader() {
            return new ListItemReader<>(IntStream.rangeClosed(1, 10).boxed().toList());
        }

        @Bean
        public PassThroughItemProcessor<Integer> itemProcessor() {
            return new PassThroughItemProcessor<>();
        }

        @Bean
        public CompositeItemWriter<Integer> itemWriter() {
            return new CompositeItemWriterBuilder<Integer>()
                    .delegates(itemWriter1(), itemWriter2())
                    .build();
        }

        @Bean
        public ListItemWriter<Integer> itemWriter1() {
            return new ListItemWriter<>();
        }

        @Bean
        public ListItemWriter<Integer> itemWriter2() {
            return new ListItemWriter<>();
        }

        @Bean
        public Step step() {
            return new StepBuilder("test", jobRepository)
                    .<Integer, Integer>chunk(3)
                    .reader(itemReader())
                    .processor(itemProcessor())
                    .writer(itemWriter())
                    .build();
        }

        @Bean
        public Job job() {
            return new JobBuilder("test", jobRepository).start(step()).build();
        }
    }
}
