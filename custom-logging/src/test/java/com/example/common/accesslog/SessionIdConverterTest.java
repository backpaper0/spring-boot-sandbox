package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;

import ch.qos.logback.access.spi.IAccessEvent;

public class SessionIdConverterTest {

	SessionIdConverter sut = new SessionIdConverter();

	@Test
	void リクエストからセッションIDを取り出して返す() {
		IAccessEvent event = mock(IAccessEvent.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(event.getRequest()).thenReturn(request);
		when(request.getAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME))
				.thenReturn("00000000-0000-0000-0000-000000000000");

		String actual = sut.convert(event);

		assertEquals("00000000-0000-0000-0000-000000000000", actual);

		verify(event).getRequest();
		verify(request).getAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME);
		verifyNoMoreInteractions(event, request);
	}

	@Test
	void リクエストにセッションIDが入っていない場合はNA() {
		IAccessEvent event = mock(IAccessEvent.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		when(event.getRequest()).thenReturn(request);
		when(request.getAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME))
				.thenReturn(null);

		String actual = sut.convert(event);

		assertEquals(IAccessEvent.NA, actual);

		verify(event).getRequest();
		verify(request).getAttribute(SessionIdConverter.REQUEST_ATTRIBUTE_NAME);
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
