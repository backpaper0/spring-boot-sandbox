package com.example.controller;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.request.BarRequest;

@RestController
@RequestMapping("/bar")
public class BarController {

	@GetMapping("/a")
	public Object get1(
			@RequestParam String param1,
			@RequestParam Integer param2,
			@RequestParam boolean param3,
			@DateTimeFormat(pattern = "uuuu-MM-dd") @RequestParam LocalDate param4) {
		return Map.of(
				"param1", param1,
				"param2", param2,
				"param3", param3,
				"param4", param4);
	}

	@GetMapping("/b")
	public Object get2(BarRequest request) {
		return request;
	}
}
