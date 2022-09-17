package com.example.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.FooRequest;
import com.example.response.FooResponse;

@RestController
@RequestMapping("/foo")
public class FooController {

	@GetMapping
	public FooResponse get() {
		return new FooResponse(
				"foo",
				12345, 23456L, new BigInteger("34567"), new BigDecimal("456.78"),
				true,
				LocalDateTime.now(), LocalDate.now(), LocalTime.now(),
				List.of("foo", "bar", "baz"));
	}

	@PostMapping
	public FooResponse post(@Validated @RequestBody FooRequest request) {
		return new FooResponse(
				request.getProp1(),
				request.getProp2(),
				request.getProp3(),
				request.getProp4(),
				request.getProp5(),
				request.isProp6(),
				request.getProp7(),
				request.getProp8(),
				request.getProp9(),
				request.getProp10());
	}
}
