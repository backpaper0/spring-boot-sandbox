package com.example;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HelloTest {

	@LocalServerPort
	int port;

	@Test
	void testName() throws Exception {
		WebClient client = WebClient.builder().baseUrl("http://localhost:" + port).build();
		HttpServiceProxyFactory factory = HttpServiceProxyFactory
				.builder(WebClientAdapter.forClient(client)).build();
		Hello hello = factory.createClient(Hello.class);
		Map<String, String> said = hello.sayHello();
		assertEquals(Map.of("message", "Hello World"), said);
	}
}
