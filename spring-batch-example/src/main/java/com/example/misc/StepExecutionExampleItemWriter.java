package com.example.misc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.step.StepExecution;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
public class StepExecutionExampleItemWriter implements ItemWriter<Integer> {

    @Value("#{stepExecution}")
    private StepExecution stepExecution;

    @Override
    public void write(Chunk<? extends Integer> chunk) throws Exception {
        log.info("{}", stepExecution);
        log.info("{}", chunk);
    }
}
