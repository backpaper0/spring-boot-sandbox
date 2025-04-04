package com.example;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Objects;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class RoleSetter implements BeanPostProcessor {

    private static final Log log = LogFactory.getLog(RoleSetter.class);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource dataSource) {
            return new DataSourceWrapper(dataSource);
        }
        return bean;
    }

    private record DataSourceWrapper(DataSource dataSource) implements DataSource {

        private static void setRole(Connection conn) throws SQLException {
            var role = Objects.requireNonNull(SpeciesHolder.get());
            var query = "set role '%s'".formatted(role);
            if (log.isInfoEnabled()) {
                log.info(query);
            }
            try (var st = conn.createStatement()) {
                st.executeUpdate(query);
            }
        }

        @Override
        public Logger getParentLogger() throws SQLFeatureNotSupportedException {
            return dataSource.getParentLogger();
        }

        @Override
        public <T> T unwrap(Class<T> iface) throws SQLException {
            return dataSource.unwrap(iface);
        }

        @Override
        public boolean isWrapperFor(Class<?> iface) throws SQLException {
            return dataSource.isWrapperFor(iface);
        }

        @Override
        public Connection getConnection() throws SQLException {
            var conn = dataSource.getConnection();
            setRole(conn);
            return conn;
        }

        @Override
        public Connection getConnection(String username, String password) throws SQLException {
            var conn = dataSource.getConnection(username, password);
            setRole(conn);
            return conn;
        }

        @Override
        public PrintWriter getLogWriter() throws SQLException {
            return dataSource.getLogWriter();
        }

        @Override
        public void setLogWriter(PrintWriter out) throws SQLException {
            dataSource.setLogWriter(out);
        }

        @Override
        public void setLoginTimeout(int seconds) throws SQLException {
            dataSource.setLoginTimeout(seconds);
        }

        @Override
        public int getLoginTimeout() throws SQLException {
            return dataSource.getLoginTimeout();
        }
    }
}
