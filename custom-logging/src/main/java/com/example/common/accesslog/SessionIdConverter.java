package com.example.common.accesslog;

import ch.qos.logback.access.pattern.AccessConverter;
import ch.qos.logback.access.spi.IAccessEvent;
import jakarta.servlet.http.HttpServletRequest;

public class SessionIdConverter extends AccessConverter {

	static final String REQUEST_ATTRIBUTE_NAME = SessionIdConverter.class.getName();

	@Override
	public String convert(IAccessEvent event) {
		HttpServletRequest request = event.getRequest();
		if (request != null) {
			Object value = request.getAttribute(REQUEST_ATTRIBUTE_NAME);
			if (value instanceof String) {
				return (String) value;
			}
		}
		return IAccessEvent.NA;
	}
}
