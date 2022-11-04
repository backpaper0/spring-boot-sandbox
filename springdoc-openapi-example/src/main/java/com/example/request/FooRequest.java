package com.example.request;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FooRequest {

	@NotNull
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
