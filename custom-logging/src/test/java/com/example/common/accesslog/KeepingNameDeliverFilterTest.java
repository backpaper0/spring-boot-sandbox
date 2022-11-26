package com.example.common.accesslog;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.common.session.NameKeeper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@SpringJUnitConfig(classes = { KeepingNameDeliverFilter.class })
public class KeepingNameDeliverFilterTest {

	@Autowired
	KeepingNameDeliverFilter sut;
	@MockBean
	NameKeeper nameKeeper;

	@Test
	void 保持している名前をリクエストへセットする() throws Exception {
		when(nameKeeper.getName()).thenReturn("testname");

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpSession session = mock(HttpSession.class);
		when(request.getSession(false)).thenReturn(session);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		sut.doFilter(request, response, chain);

		verify(nameKeeper).getName();
		verify(request).setAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME, "testname");
		verify(request).getSession(false);
		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(nameKeeper, request, response, chain, session);
	}

	@Test
	void 保持している名前がなければリクエストへセットしない() throws Exception {
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getSession(false)).thenReturn(null);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		sut.doFilter(request, response, chain);

		verify(request).getSession(false);
		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(nameKeeper, request, response, chain);
	}
}
