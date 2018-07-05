package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

@Component
public class HelloTasklet implements Tasklet {

    private final DataSource dataSource;

    public HelloTasklet(@Secondary final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public RepeatStatus execute(final StepContribution contribution,
            final ChunkContext chunkContext)
            throws Exception {
        try (Connection con = dataSource.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT DATABASE()");
                ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                System.out.printf("Hello, %s!!!%n", rs.getString(1));
            }
        }
        return RepeatStatus.FINISHED;
    }
}
