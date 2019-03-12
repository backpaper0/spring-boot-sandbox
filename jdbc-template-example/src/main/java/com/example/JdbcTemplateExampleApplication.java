package com.example;

import java.sql.ResultSetMetaData;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;

@SpringBootApplication
public class JdbcTemplateExampleApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(JdbcTemplateExampleApplication.class, args);
    }

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateExampleApplication(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(final String... args) throws Exception {
        final RowCallbackHandler handler = rs -> {
            final ResultSetMetaData meta = rs.getMetaData();
            final int columnCount = meta.getColumnCount();
            do {
                final Object[] os = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    final Object o = rs.getObject(i + 1);
                    os[i] = o;
                }
                System.out.println(Arrays.toString(os));
            } while (rs.next());
        };
        jdbcTemplate.query("SELECT * FROM example ORDER BY id ASC", handler);
    }
}
