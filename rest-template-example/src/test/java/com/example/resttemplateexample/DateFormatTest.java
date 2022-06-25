package com.example.resttemplateexample;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DateFormatTest {

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper objectMapper = new ObjectMapper();
	MockRestServiceServer mockServer;

	@BeforeEach
	void init() {
		mockServer = MockRestServiceServer.createServer(restTemplate);
	}

	@Test
	void test() throws Exception {
		mockServer.expect(requestTo("/date-format"))
				.andExpect(method(HttpMethod.GET))
				.andRespond(withStatus(HttpStatus.OK)
						.contentType(MediaType.APPLICATION_JSON)
						.body(objectMapper.writeValueAsString(Map.of(
								"dateTime", "2022-06-25 12:34",
								"date", "2022-06-25",
								"time", "12:34"))));

		MyResponse response = restTemplate.getForObject("/date-format", MyResponse.class);

		mockServer.verify();

		MyResponse expected = new MyResponse(
				LocalDateTime.of(2022, 06, 25, 12, 34),
				LocalDate.of(2022, 6, 25),
				LocalTime.of(12, 34));

		assertEquals(expected, response);
	}

	public record MyResponse(
			@JsonFormat(pattern = "uuuu-MM-dd HH:mm") LocalDateTime dateTime,
			@JsonFormat(pattern = "uuuu-MM-dd") LocalDate date,
			@JsonFormat(pattern = "HH:mm") LocalTime time) {
	}
}
