package com.example.common.httpclient;

import org.springframework.stereotype.Component;

@Component
public class SystemNanoTimeProvider {

	public long provide() {
		return System.nanoTime();
	}
}
