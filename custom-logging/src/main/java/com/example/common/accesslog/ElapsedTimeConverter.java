package com.example.common.accesslog;

import java.util.concurrent.TimeUnit;

import ch.qos.logback.access.common.pattern.AccessConverter;
import ch.qos.logback.access.common.spi.IAccessEvent;
import jakarta.servlet.http.HttpServletRequest;

public class ElapsedTimeConverter extends AccessConverter {

    public static final String REQUEST_ATTRIBUTE_NAME = ElapsedTimeConverter.class.getName();

    @Override
    public String convert(IAccessEvent event) {
        HttpServletRequest request = event.getRequest();
        if (request != null) {
            Object value = request.getAttribute(REQUEST_ATTRIBUTE_NAME);
            if (value instanceof Long) {
                long totalRoundTripTime = TimeUnit.NANOSECONDS.toMillis((Long) value);
                return Long.toString(event.getElapsedTime() - totalRoundTripTime);
            }
        }
        return Long.toString(event.getElapsedTime());
    }
}
