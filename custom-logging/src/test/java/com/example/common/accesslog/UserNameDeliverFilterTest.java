package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(classes = { UserNameDeliverFilter.class })
public class UserNameDeliverFilterTest {

	@Autowired
	UserNameDeliverFilter sut;

	@MockBean
	SecurityProperties securityProperties;

	@Test
	void 認証済みユーザー名をリクエストへセットする() throws Exception {

		Authentication authentication = mock(Authentication.class);
		when(authentication.getName()).thenReturn("testuser");
		SecurityContextHolder.getContext().setAuthentication(authentication);

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		sut.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verify(authentication).getName();
		verify(request).setAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME, "testuser");
		verifyNoMoreInteractions(request, response, chain, securityProperties);
	}

	@Test
	void 認証済みユーザー名が取得できない場合はリクエストへセットしない() throws Exception {

		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		FilterChain chain = mock(FilterChain.class);

		sut.doFilter(request, response, chain);

		verify(chain).doFilter(request, response);
		verifyNoMoreInteractions(request, response, chain, securityProperties);
	}

	@Test
	void フィルターの順序() {
		SecurityProperties.Filter filter = new SecurityProperties.Filter();
		filter.setOrder(100);
		when(securityProperties.getFilter()).thenReturn(filter);

		int order = sut.getOrder();

		assertEquals(101, order);

		verify(securityProperties).getFilter();
		verifyNoMoreInteractions(securityProperties);
	}
}
