package com.example.routing;

import org.springframework.core.InfrastructureProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RoutingDataSource extends AbstractRoutingDataSource implements InfrastructureProxy {

    private static final ThreadLocal<LookupKey> lookupKeys = new ThreadLocal<>();

    @Override
    protected Object determineCurrentLookupKey() {
        return lookupKeys.get();
    }

    @Override
    public Object getWrappedObject() {
        return determineCurrentLookupKey();
    }

    public static LookupKey set(final LookupKey lookupKey) {
        final LookupKey current = lookupKeys.get();
        lookupKeys.set(lookupKey);
        return current;
    }
}
