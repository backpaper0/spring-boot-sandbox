package com.example;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

public class PrintCountTask implements Tasklet {

	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		Long count = contribution.getStepExecution().getJobParameters().getLong("run.id");
		System.out.println(count);

		if (count % 10 == 0) {
			throw new RuntimeException();
		}

		return RepeatStatus.FINISHED;
	}
}
