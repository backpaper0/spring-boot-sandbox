package com.example.misc;

import java.util.Map;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.scope.context.StepContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * {@link StepScope}だと{@link StepContext}が持つ値をインジェクションできるっぽい。
 *
 */
@Component
@StepScope
@Slf4j
public class InjectionExampleTasklet1 implements Tasklet {

	@Value("#{jobInstanceId}")
	private Long jobInstanceId;
	@Value("#{jobName}")
	private String jobName;
	@Value("#{jobParameters}")
	private Map<String, Object> jobParameters;
	@Value("#{jobExecutionContext}")
	private Map<String, Object> jobExecutionContext;
	@Value("#{partitionPlan}")
	private Map<String, Object> partitionPlan;
	@Value("#{id}")
	private String id;
	@Value("#{stepName}")
	private String stepName;
	@Value("#{stepExecution}")
	private StepExecution stepExecution;
	//	@Value("#{systemProperties}")
	//	private Properties systemProperties;

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		log.info("jobInstanceId = {}", jobInstanceId);
		log.info("jobName = {}", jobName);
		log.info("jobParameters = {}", jobParameters);
		log.info("jobExecutionContext = {}", jobExecutionContext);
		log.info("partitionPlan = {}", partitionPlan);
		log.info("id = {}", id);
		log.info("stepName = {}", stepName);
		log.info("stepExecution = {}", stepExecution);
		//		log.info("systemProperties = {}", systemProperties);
		return RepeatStatus.FINISHED;
	}
}
