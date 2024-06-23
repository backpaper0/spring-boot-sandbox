package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;

import ch.qos.logback.access.common.spi.IAccessEvent;
import jakarta.servlet.http.HttpServletRequest;

public class KeepingNameConverterTest {

    KeepingNameConverter sut = new KeepingNameConverter();

    @Test
    void リクエストから名前を取り出して返す() {
        IAccessEvent event = mock(IAccessEvent.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(event.getRequest()).thenReturn(request);
        when(request.getAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME))
                .thenReturn("testname");

        String actual = sut.convert(event);

        assertEquals("testname", actual);

        verify(event).getRequest();
        verify(request).getAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME);
        verifyNoMoreInteractions(event, request);
    }

    @Test
    void リクエストに名前が入っていない場合はNA() {
        IAccessEvent event = mock(IAccessEvent.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(event.getRequest()).thenReturn(request);
        when(request.getAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME))
                .thenReturn(null);

        String actual = sut.convert(event);

        assertEquals(IAccessEvent.NA, actual);

        verify(event).getRequest();
        verify(request).getAttribute(KeepingNameConverter.REQUEST_ATTRIBUTE_NAME);
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
