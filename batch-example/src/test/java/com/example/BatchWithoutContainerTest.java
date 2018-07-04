package com.example;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class BatchWithoutContainerTest {

    @Test
    public void withoutContainer() throws Exception {

        final MapJobRepositoryFactoryBean factoryBean = new MapJobRepositoryFactoryBean();
        final JobRepository jobRepository = factoryBean.getObject();
        final SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();

        final JobBuilderFactory jobs = new JobBuilderFactory(jobRepository);
        final StepBuilderFactory steps = new StepBuilderFactory(jobRepository,
                factoryBean.getTransactionManager());

        final Step step1 = steps.get("hello").tasklet(new PrintTasklet("Hello")).build();
        final Step step2 = steps.get("world").tasklet(new PrintTasklet("World")).build();
        final Job job = jobs.get("hello world").start(step1).next(step2).build();

        final JobParameters jobParameters = new JobParameters();
        final JobExecution jobExecution = jobLauncher.run(job, jobParameters);
        System.out.println(jobExecution);
    }

    static class PrintTasklet implements Tasklet {

        private final Logger logger = LoggerFactory.getLogger(PrintTasklet.class);

        private final String word;

        public PrintTasklet(final String word) {
            this.word = word;
        }

        @Override
        public RepeatStatus execute(final StepContribution contribution,
                final ChunkContext chunkContext)
                throws Exception {
            if (logger.isInfoEnabled()) {
                logger.info(word);
            }
            return RepeatStatus.FINISHED;
        }
    }
}
