package com.example.core.jdbc;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource {

	private final ThreadLocal<Routing> routing = new ThreadLocal<>();

	@Override
	protected Object determineCurrentLookupKey() {
		return routing.get();
	}

	public boolean routeIsNull() {
		return routing.get() == null;
	}

	public void setRouting(Routing routing) {
		this.routing.set(routing);
	}

	public void clearRouting() {
		routing.remove();
	}
}
