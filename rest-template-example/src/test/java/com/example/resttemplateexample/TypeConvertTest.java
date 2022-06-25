package com.example.resttemplateexample;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TypeConvertTest {

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper objectMapper = new ObjectMapper();
	MockRestServiceServer mockServer;

	@BeforeEach
	void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void test() throws Exception {
		mockServer.expect(requestTo("/integer"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(objectMapper.writeValueAsString(Map.of(
								"integer", 1234))));

		MyResponse response = restTemplate.getForObject("/integer", MyResponse.class);

		mockServer.verify();

		MyResponse expected = new MyResponse(1234);

		assertEquals(expected, response);
	}

	@Test
	void testTypeMismatch() throws Exception {
		mockServer.expect(requestTo("/integer"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(objectMapper.writeValueAsString(Map.of(
								"integer", "test"))));

		assertThrowsExactly(RestClientException.class, () -> restTemplate.getForObject("/integer", MyResponse.class));

		mockServer.verify();
	}

	public record MyResponse(Integer integer) {
	}
}
