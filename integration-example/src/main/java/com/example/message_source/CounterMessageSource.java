package com.example.message_source;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.integration.endpoint.AbstractMessageSource;
import org.springframework.stereotype.Component;

@Component
public class CounterMessageSource extends AbstractMessageSource<Integer> {

	private final AtomicInteger count = new AtomicInteger();

	@Override
	public String getComponentType() {
		return "inbound-channel-adapter";
	}

	@Override
	protected Object doReceive() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			logger.info(e.getMessage());
		}
		return count.incrementAndGet();
	}
}
