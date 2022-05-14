package com.example.common.accesslog;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.web.servlet.filter.OrderedFilter;
import org.springframework.stereotype.Component;

@Component
public class SessionIdDeliverFilter implements OrderedFilter {

	@Autowired
	private SessionProperties sessionProperties;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			if (session != null) {
				String id = session.getId();
				request.setAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME, id);
			}
		}
	}

	@Override
	public int getOrder() {
		return sessionProperties.getServlet().getFilterOrder() + 1;
	}
}
