package com.example.multiwriter;

import java.util.List;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.batch.item.support.ListItemWriter;
import org.springframework.batch.item.support.PassThroughItemProcessor;
import org.springframework.batch.item.support.builder.ClassifierCompositeItemWriterBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.Classifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class MultiWriterBatch {

	@Autowired
	private JobRepository jobRepository;
	@Autowired
	private PlatformTransactionManager transactionManager;

	@Bean
	@StepScope
	public ItemReader<String> multiWriterItemReader() {
		return new ListItemReader<>(List.of("foo", "bar", "baz", "qux"));
	}

	@Bean
	public ItemProcessor<String, String> multiWriterItemProcessor() {
		return new PassThroughItemProcessor<>();
	}

	@Bean
	@StepScope
	public ListItemWriter<String> multiWriterItemWriter1() {
		return new ListItemWriter<>();
	}

	@Bean
	@StepScope
	public ListItemWriter<String> multiWriterItemWriter2() {
		return new ListItemWriter<>();
	}

	@Bean
	public ItemWriter<String> multiWriterItemWriter() {
		Classifier<String, ItemWriter<? super String>> classifier = item -> {
			if (item.startsWith("b")) {
				return multiWriterItemWriter1();
			}
			return multiWriterItemWriter2();
		};
		return new ClassifierCompositeItemWriterBuilder<String>()
				.classifier(classifier)
				.build();
	}

	@Bean
	public Step multiWriterStep() {
		return new StepBuilder("MultiWriter", jobRepository)
				.<String, String> chunk(1, transactionManager)
				.reader(multiWriterItemReader())
				.processor(multiWriterItemProcessor())
				.writer(multiWriterItemWriter())
				.listener(new StepExecutionListener() {
					@Override
					public ExitStatus afterStep(StepExecution stepExecution) {
						System.out.println(multiWriterItemWriter1().getWrittenItems());
						System.out.println(multiWriterItemWriter2().getWrittenItems());
						return null;
					}
				})
				.build();
	}

	@Bean
	public Job multiWriterJob() {
		return new JobBuilder("MultiWriter", jobRepository)
				.start(multiWriterStep())
				.build();
	}
}
