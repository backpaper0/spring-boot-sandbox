package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import ch.qos.logback.access.spi.IAccessEvent;
import jakarta.servlet.http.HttpServletRequest;

public class UserNameConverterTest {

	UserNameConverter sut = new UserNameConverter();

	@Test
	void リクエストからユーザー名を取り出して返す() {
		IAccessEvent event = mock(IAccessEvent.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(event.getRequest()).thenReturn(request);
		when(request.getAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME))
				.thenReturn("testuser");

		String actual = sut.convert(event);

		assertEquals("testuser", actual);

		verify(event).getRequest();
		verify(request).getAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME);
		verifyNoMoreInteractions(event, request);
	}

	@Test
	void リクエストにユーザー名が入っていない場合はNA() {
		IAccessEvent event = mock(IAccessEvent.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(event.getRequest()).thenReturn(request);
		when(request.getAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME))
				.thenReturn(null);

		String actual = sut.convert(event);

		assertEquals(IAccessEvent.NA, actual);

		verify(event).getRequest();
		verify(request).getAttribute(UserNameConverter.REQUEST_ATTRIBUTE_NAME);
		verifyNoMoreInteractions(event, request);
	}

	@Test
	void リクエストを取得できない場合はNA() {
		IAccessEvent event = mock(IAccessEvent.class);
		when(event.getRequest()).thenReturn(null);

		String actual = sut.convert(event);

		assertEquals(IAccessEvent.NA, actual);

		verify(event).getRequest();
		verifyNoMoreInteractions(event);
	}
}
