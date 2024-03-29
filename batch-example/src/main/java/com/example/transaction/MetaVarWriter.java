package com.example.transaction;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Component
public class MetaVarWriter implements ItemWriter<String> {

	private final JdbcTemplate jdbcTemplate;
	private final PlatformTransactionManager transactionManager;
	private static final Logger logger = LoggerFactory.getLogger(MetaVarWriter.class);

	public MetaVarWriter(final DataSource dataSource,
			final PlatformTransactionManager transactionManager) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
		this.transactionManager = transactionManager;
	}

	@Override
	public void write(Chunk<? extends String> chunk) throws Exception {
		TransactionDefinition definition = new DefaultTransactionDefinition(
				TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(definition);
		try {
			for (String item : chunk) {
				if (item.equals("waldo")) {
					throw new RuntimeException("waldo");
				}
				jdbcTemplate.update("INSERT INTO metavar (varname) VALUES (?)", item);
			}
			transactionManager.commit(status);
		} catch (final Exception e) {
			transactionManager.rollback(status);
			if (logger.isWarnEnabled()) {
				logger.warn("", e);
			}
		}
	}
}
