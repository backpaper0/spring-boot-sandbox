package com.example.tasklet;

import org.springframework.batch.core.step.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.infrastructure.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class HelloTasklet implements Tasklet {

	@Override
	public RepeatStatus execute(final StepContribution contribution,
			final ChunkContext chunkContext)
			throws Exception {

		System.out.println("Hello Tasklet");

		return RepeatStatus.FINISHED;
	}
}
