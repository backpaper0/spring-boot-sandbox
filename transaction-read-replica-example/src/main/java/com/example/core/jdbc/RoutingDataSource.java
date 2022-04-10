package com.example.core.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

	private final ThreadLocal<Boolean> readReplica = new ThreadLocal<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return readReplica.get();
	}

	public void usePrimary() {
		readReplica.set(Boolean.FALSE);
	}

	public void useReadReplica() {
		readReplica.set(Boolean.TRUE);
	}

	public void clear() {
		readReplica.remove();
	}
}
