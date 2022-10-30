package com.example.resttemplateexample;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.util.Map;

import jakarta.validation.constraints.NotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
public class ValidationExplicitTest {

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper objectMapper = new ObjectMapper();
	MockRestServiceServer mockServer;

	@Autowired
	SmartValidator validator;

	@BeforeEach
	void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void test() throws Exception {
		mockServer.expect(requestTo("/message"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(objectMapper.writeValueAsString(Map.of(
								"integer", 1234))));

		MyResponse response = restTemplate.getForObject("/message", MyResponse.class);

		mockServer.verify();

		BindingResult errors = new BeanPropertyBindingResult(response, "");
		validator.validate(response, errors);

		assertTrue(errors.hasFieldErrors());
		FieldError fieldError = errors.getFieldError("name");
		assertEquals("null は許可されていません", fieldError.getDefaultMessage());
	}

	public record MyResponse(
			@NotNull String name,
			Integer integer) {
	}
}
