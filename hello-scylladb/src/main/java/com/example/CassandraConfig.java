package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cassandra.CassandraProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

	@Autowired
	private CassandraProperties cassandraProperties;

	@Override
	protected String getKeyspaceName() {
		return cassandraProperties.getKeyspaceName();
	}
}
