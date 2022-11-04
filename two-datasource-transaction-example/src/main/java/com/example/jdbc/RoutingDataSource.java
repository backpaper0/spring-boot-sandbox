package com.example.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

	private static final ThreadLocal<DataSources> lookupKeys = new ThreadLocal<>();

	public static void setLookupKey(final DataSources dataSource) {
		lookupKeys.set(dataSource);
	}

	public static void removeLookupKey() {
		lookupKeys.remove();
	}

	@Override
	protected Object determineCurrentLookupKey() {
		return lookupKeys.get();
	}
}
