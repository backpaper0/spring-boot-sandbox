package com.example.common.accesslog;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.common.session.NameKeeper;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
@Order(OrderedFilter.REQUEST_WRAPPER_FILTER_MAX_ORDER + 1)
public class KeepingNameDeliverFilter implements Filter {

	@Autowired
	private NameKeeper nameKeeper;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			String name = nameKeeper.getName();
			if (name != null) {
				request.setAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME, name);
			}
		}
	}
}
