package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.session.SessionProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = { SessionIdDeliverFilter.class })
public class SessionIdDeliverFilterTest {

	@Autowired
	SessionIdDeliverFilter sut;

	@MockBean
	SessionProperties sessionProperties;

	@Test
	void セッションIDをリクエストへセットする() throws Exception {

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		HttpSession session = mock(HttpSession.class);

		when(request.getSession(false)).thenReturn(session);
		when(session.getId()).thenReturn("asdfzxcv");

		sut.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verify(request).getSession(false);
		verify(session).getId();
		verify(request).setAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME, "asdfzxcv");
		verifyNoMoreInteractions(request, response, chain, session, sessionProperties);
	}

	@Test
	void セッションが取得できない場合はリクエストへセットしない() throws Exception {

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		HttpSession session = mock(HttpSession.class);

		when(request.getSession(false)).thenReturn(null);

		sut.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verify(request).getSession(false);
		verifyNoMoreInteractions(request, response, chain, session, sessionProperties);
	}

	@Test
	void フィルターの順序() {
		SessionProperties.Servlet servlet = new SessionProperties.Servlet();
		servlet.setFilterOrder(100);
		when(sessionProperties.getServlet()).thenReturn(servlet);

		int order = sut.getOrder();

		assertEquals(101, order);

		verify(sessionProperties).getServlet();
		verifyNoMoreInteractions(sessionProperties);
	}
}
