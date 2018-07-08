package com.example.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.example.jdbc.UseFirstDataSource;
import com.example.jdbc.UseSecondDataSource;

@Component
public class ExampleDao {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final DataSource dataSource;

    public ExampleDao(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @UseFirstDataSource
    public void selectFirstDatabase() {
        selectDatabaseInternal();
    }

    @UseSecondDataSource
    public void selectSecondDatabase() {
        selectDatabaseInternal();
    }

    private void selectDatabaseInternal() {
        try (Connection con = dataSource.getConnection();
                PreparedStatement pst = con.prepareStatement("SELECT DATABASE()");
                ResultSet rs = pst.executeQuery()) {
            if (rs.next() == false) {
                throw new RuntimeException();
            }
            logger.info(rs.getString(1));
        } catch (final SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
