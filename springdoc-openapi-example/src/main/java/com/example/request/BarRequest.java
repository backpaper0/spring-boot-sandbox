package com.example.request;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class BarRequest {

	private String param1;

	private Integer param2;

	private boolean param3;

	@DateTimeFormat(pattern = "uuuu-MM-dd")
	private LocalDate param4;
}
