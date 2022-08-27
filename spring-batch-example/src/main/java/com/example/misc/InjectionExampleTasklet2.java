package com.example.misc;

import java.util.Map;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.JobContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link JobScope}だと{@link JobContext}が持つ値をインジェクションできるっぽい。
 *
 */
@Component
@JobScope
@Slf4j
public class InjectionExampleTasklet2 implements Tasklet {

	@Value("#{id}")
	private String id;
	@Value("#{jobName}")
	private String jobName;
	@Value("#{jobParameters}")
	private Map<String, Object> jobParameters;
	@Value("#{jobExecutionContext}")
	private Map<String, Object> jobExecutionContext;
	@Value("#{jobExecution}")
	private JobExecution jobExecution;
	//	@Value("#{systemProperties}")
	//	private Properties systemProperties;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("id = {}", id);
		log.info("jobName = {}", jobName);
		log.info("jobParameters = {}", jobParameters);
		log.info("jobExecutionContext = {}", jobExecutionContext);
		log.info("jobExecution = {}", jobExecution);
		//		log.info("systemProperties = {}", systemProperties);
		return RepeatStatus.FINISHED;
	}
}
