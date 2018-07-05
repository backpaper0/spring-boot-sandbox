package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HelloTasklet implements Tasklet {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    public HelloTasklet(@Secondary final DataSource dataSource) {
        this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
    }

    @Override
    @Transactional("secondaryPlatformTransactionManager")
    public RepeatStatus execute(final StepContribution contribution,
            final ChunkContext chunkContext)
            throws Exception {
        logger.info("Start HelloTasklet#execute");
        try (Connection con = dataSource.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT DATABASE()");
                ResultSet rs = pst.executeQuery()) {
            if (rs.next() == false) {
                throw new RuntimeException();
            }
            logger.info("Hello, {} DataSource!!!", rs.getString(1));
        }
        logger.info("End HelloTasklet#execute");
        return RepeatStatus.FINISHED;
    }
}
