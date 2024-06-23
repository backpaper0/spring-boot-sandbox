package com.example.common.accesslog;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import ch.qos.logback.access.common.spi.IAccessEvent;
import jakarta.servlet.http.HttpServletRequest;

public class ElapsedTimeConverterTest {

    ElapsedTimeConverter sut = new ElapsedTimeConverter();

    @Test
    void リクエスト処理に要した時間から外部APIの呼び出しにかかった時間を差し引く() {
        IAccessEvent event = mock(IAccessEvent.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(event.getRequest()).thenReturn(request);
        when(event.getElapsedTime()).thenReturn(TimeUnit.SECONDS.toMillis(3L));
        when(request.getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME))
                .thenReturn(TimeUnit.SECONDS.toNanos(2L));

        String actual = sut.convert(event);

        assertEquals("1000", actual);

        verify(event).getRequest();
        verify(event).getElapsedTime();
        verify(request).getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME);
        verifyNoMoreInteractions(event, request);
    }

    @Test
    void 外部APIの呼び出しを行っていない場合はそのまま() {
        IAccessEvent event = mock(IAccessEvent.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(event.getRequest()).thenReturn(request);
        when(event.getElapsedTime()).thenReturn(TimeUnit.SECONDS.toMillis(1L));

        String actual = sut.convert(event);

        assertEquals("1000", actual);

        verify(event).getRequest();
        verify(event).getElapsedTime();
        verify(request).getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME);
        verifyNoMoreInteractions(event, request);
    }

    @Test
    void リクエストを取得できない場合もそのまま() {
        IAccessEvent event = mock(IAccessEvent.class);
        when(event.getRequest()).thenReturn(null);
        when(event.getElapsedTime()).thenReturn(TimeUnit.SECONDS.toMillis(1L));

        String actual = sut.convert(event);

        assertEquals("1000", actual);

        verify(event).getRequest();
        verify(event).getElapsedTime();
        verifyNoMoreInteractions(event);
    }
}
