package com.example.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FooResponse {

	private String prop1;

	private Integer prop2;

	private Long prop3;

	private BigInteger prop4;

	private BigDecimal prop5;

	private boolean prop6;

	@JsonFormat(pattern = "uuuu-MM-dd'T'HH:mm:ss")
	private LocalDateTime prop7;

	@JsonFormat(pattern = "uuuu-MM-dd")
	private LocalDate prop8;

	@JsonFormat(pattern = "HH:mm:ss")
	private LocalTime prop9;

	private List<String> prop10;
}
