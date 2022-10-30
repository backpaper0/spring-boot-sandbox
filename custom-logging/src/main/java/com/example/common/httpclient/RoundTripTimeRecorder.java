package com.example.common.httpclient;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;

import com.example.common.accesslog.ElapsedTimeConverter;

@Component
public class RoundTripTimeRecorder implements ClientHttpRequestInterceptor {

	@Autowired
	private HttpServletRequest httpServletRequest;
	@Autowired
	private SystemNanoTimeProvider systemNanoTimeProvider;

	// ClientHttpResponseのラッパーを返すために出る警告を抑止。
	// ラッパーをcloseすると内部のClientHttpResponseもcloseされるため問題はない。
	@SuppressWarnings("resource")
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		Recorder recorder = new Recorder();
		ClientHttpResponse response = execution.execute(request, body);
		return new ClientHttpResponseWrapper(response, recorder);
	}

	private class Recorder implements Runnable {

		private final long startedTimeNanos = systemNanoTimeProvider.provide();
		private boolean recorded;

		@Override
		public void run() {
			if (recorded) {
				return;
			}
			recorded = true;
			long roundTripTime = systemNanoTimeProvider.provide() - startedTimeNanos;
			Object maybeTotalRoundTripTime = httpServletRequest
					.getAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME);
			long totalRoundTripTime;
			if (maybeTotalRoundTripTime instanceof Long) {
				totalRoundTripTime = ((Long) maybeTotalRoundTripTime);
			} else {
				totalRoundTripTime = 0L;
			}
			httpServletRequest.setAttribute(ElapsedTimeConverter.REQUEST_ATTRIBUTE_NAME,
					totalRoundTripTime + roundTripTime);
		}
	}
}
