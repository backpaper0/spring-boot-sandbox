package app.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/*
 * バッチを試す。
 * とりあえずログを出力するだけ。
 * Appを起動したら即実行されるっぽい。
 *
 * ドキュメントには
 * 
 * > By default it executes all Jobs in the application context on startup 
 * 
 * とあった。
 */
@Component
public class SimpleBatch {

    @Autowired
    private JobBuilderFactory jobs;

    @Autowired
    private StepBuilderFactory steps;

    @Bean
    public Job job() throws Exception {

        Tasklet tasklet = new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution,
                    ChunkContext chunkContext) throws Exception {
                Logger logger = LoggerFactory.getLogger(SimpleBatch.class);
                logger.info("Hello, Spring Batch!!");
                return RepeatStatus.FINISHED;
            }
        };

        Step step = steps.get("step").tasklet(tasklet).build();

        return jobs.get("job").start(step).build();
    }

}
