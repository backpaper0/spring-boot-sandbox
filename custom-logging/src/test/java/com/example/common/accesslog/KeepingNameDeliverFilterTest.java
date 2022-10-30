package com.example.common.accesslog;

import static org.mockito.Mockito.*;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.common.session.NameKeeper;

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
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		sut.doFilter(request, response, chain);

		verify(nameKeeper).getName();
		verify(request).setAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME, "testname");
		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(nameKeeper, request, response, chain);
	}

	@Test
	void 保持している名前がなければリクエストへセットしない() throws Exception {
		when(nameKeeper.getName()).thenReturn(null);

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		sut.doFilter(request, response, chain);

		verify(nameKeeper).getName();
		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(nameKeeper, request, response, chain);
	}
}
