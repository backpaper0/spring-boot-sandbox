package com.example;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class DataSourceWrapperConfig implements BeanPostProcessor {

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName)
			throws BeansException {
		if (bean instanceof DataSource) {
			return new Wrapper((DataSource) bean);
		}
		return bean;
	}

	private static class Wrapper implements DataSource {

		private static final Logger logger = LoggerFactory.getLogger(Wrapper.class);

		private final DataSource dataSource;

		public Wrapper(DataSource dataSource) {
			this.dataSource = dataSource;
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
			logger.info("<<<< getConnection >>>>");
			return dataSource.getConnection();
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return dataSource.getConnection(username, password);
		}

		@Override
		public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return dataSource.getParentLogger();
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
