package com.example.common.httpclient;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;

public class ClientHttpResponseWrapper implements ClientHttpResponse {

	private final ClientHttpResponse response;
	private final Runnable recorder;

	public ClientHttpResponseWrapper(ClientHttpResponse response, Runnable recorder) {
		this.response = response;
		this.recorder = recorder;
	}

	@Override
	public HttpHeaders getHeaders() {
		return response.getHeaders();
	}

	@Override
	public InputStream getBody() throws IOException {
		return response.getBody();
	}

	@Override
	public HttpStatusCode getStatusCode() throws IOException {
		return response.getStatusCode();
	}

	@Override
	public int getRawStatusCode() throws IOException {
		return response.getRawStatusCode();
	}

	@Override
	public String getStatusText() throws IOException {
		return response.getStatusText();
	}

	@Override
	public void close() {
		try {
			response.close();
		} finally {
			recorder.run();
		}
	}
}
