package com.example.webfluxexample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import reactor.core.publisher.Mono;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class WebFilterExample implements WebFilter {

	private static final Logger logger = LoggerFactory.getLogger(WebFilterExample.class);

	@Override
	public Mono<Void> filter(final ServerWebExchange exchange, final WebFilterChain chain) {
		logger.info("1");
		try {
			return chain.filter(exchange).map(a -> {
				logger.info("3");
				return a;
			});
		} finally {
			logger.info("2");
		}
	}
}
