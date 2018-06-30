package com.example.transaction;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Component;

@Component
public class PrintlnStepExecutionListener extends StepExecutionListenerSupport {

    private final JdbcTemplate jdbcTemplate;

    public PrintlnStepExecutionListener(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ExitStatus afterStep(final StepExecution stepExecution) {
        final List<String> entities = jdbcTemplate.query(
                "SELECT varname FROM metavar ORDER BY id ASC",
                new SingleColumnRowMapper<>(String.class));
        System.out.println(entities);
        return stepExecution.getExitStatus();
    }
}
