package com.example.common.httpclient;

import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

@SuppressWarnings("resource")
public class ClientHttpResponseWrapperTest {

	ClientHttpResponseWrapper sut;

	ClientHttpResponse response;
	Runnable recorder;

	@BeforeEach
	void init() throws IOException {
		response = mock(ClientHttpResponse.class);
		recorder = mock(Runnable.class);
		sut = new ClientHttpResponseWrapper(response, recorder);
		when(response.getStatusCode()).thenReturn(HttpStatus.OK);
	}

	@Test
	void getHeaders() throws Exception {
		sut.getHeaders();
		verify(response).getHeaders();
		verifyNoMoreInteractions(response, recorder);
	}

	@Test
	void getBody() throws Exception {
		sut.getBody();
		verify(response).getBody();
		verifyNoMoreInteractions(response, recorder);
	}

	@Test
	void getStatusCode() throws Exception {
		sut.getStatusCode();
		verify(response).getStatusCode();
		verifyNoMoreInteractions(response, recorder);
	}

	@Test
	void getStatusText() throws Exception {
		sut.getStatusText();
		verify(response).getStatusText();
		verifyNoMoreInteractions(response, recorder);
	}

	@Test
	void close() throws Exception {
		sut.close();
		verify(response).close();
		verify(recorder).run();
		verifyNoMoreInteractions(response, recorder);
	}
}
