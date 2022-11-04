package com.example.common.httpclient;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.common.accesslog.ElapsedTimeConverter;

import jakarta.servlet.http.HttpServletRequest;

@SpringJUnitConfig(classes = { RoundTripTimeRecorder.class })
public class RoundTripTimeRecorderTest {

	@Autowired
	RoundTripTimeRecorder sut;

	@MockBean
	HttpServletRequest mockHttpServletRequest;
	@MockBean
	SystemNanoTimeProvider mockSystemNanoTimeProvider;

	@SuppressWarnings("resource") // モックのClientHttpResponseがcloseされないために出る警告を抑止する
	@Test
	void 経過時間を計算してリクエスト属性に格納する() throws Exception {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
		when(execution.execute(any(), any())).thenReturn(response);

		when(mockSystemNanoTimeProvider.provide()).thenReturn(
				1L, // リクエスト開始時
				3L); // レスポンスのハンドリング終了時

		HttpRequest request = mock(HttpRequest.class);
		byte[] body = {};
		response = sut.intercept(request, body, execution);
		response.close();

		verify(mockSystemNanoTimeProvider, times(2)).provide();
		verify(mockHttpServletRequest).getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME);
		verify(mockHttpServletRequest)
				.setAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME, 2L);
		verifyNoMoreInteractions(mockSystemNanoTimeProvider, mockHttpServletRequest);
	}

	@SuppressWarnings("resource") // モックのClientHttpResponseがcloseされないために出る警告を抑止する
	@Test
	void 既にリクエスト属性に経過時間が記録されている場合は合算する() throws Exception {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
		when(execution.execute(any(), any())).thenReturn(response);

		when(mockSystemNanoTimeProvider.provide()).thenReturn(
				1L, // リクエスト開始時
				3L); // レスポンスのハンドリング終了時

		// 既に記録されている経過時間
		when(mockHttpServletRequest.getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME))
				.thenReturn(4L);

		HttpRequest request = mock(HttpRequest.class);
		byte[] body = {};
		response = sut.intercept(request, body, execution);
		response.close();

		verify(mockSystemNanoTimeProvider, times(2)).provide();
		verify(mockHttpServletRequest).getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME);
		verify(mockHttpServletRequest)
				.setAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME, 6L);
		verifyNoMoreInteractions(mockSystemNanoTimeProvider, mockHttpServletRequest);
	}

	@SuppressWarnings("resource") // モックのClientHttpResponseがcloseされないために出る警告を抑止する
	@Test
	void 経過時間の計算が行われるのは一度きり() throws Exception {
		ClientHttpResponse response = mock(ClientHttpResponse.class);
		ClientHttpRequestExecution execution = mock(ClientHttpRequestExecution.class);
		when(execution.execute(any(), any())).thenReturn(response);

		when(mockSystemNanoTimeProvider.provide()).thenReturn(
				1L, // リクエスト開始時
				3L); // レスポンスのハンドリング終了時

		HttpRequest request = mock(HttpRequest.class);
		byte[] body = {};
		response = sut.intercept(request, body, execution);
		response.close();
		response.close(); //2度目のclose呼び出しだが、経過時間の計算は一度しか行われない

		verify(mockSystemNanoTimeProvider, times(2)).provide();
		verify(mockHttpServletRequest).getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME);
		verify(mockHttpServletRequest)
				.setAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME, 2L);
		verifyNoMoreInteractions(mockSystemNanoTimeProvider, mockHttpServletRequest);
	}
}
