package com.example.connection.component;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class Foo {

    private final DataSource dataSource;

    public Foo(final DataSource dataSource) {
        this.dataSource = new TransactionAwareDataSourceProxy(dataSource);
    }

    public void willCommit() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
        }
    }

    public void willRollback() throws SQLException {
        try (Connection con = dataSource.getConnection()) {
            throw new RuntimeException();
        }
    }
}
