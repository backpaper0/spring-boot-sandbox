package com.example.various;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

@Data
public class RootObj {

	private String foo;
	private LocalDate bar;
	private LocalDateTime bar2;
	private LocalTime bar3;
	private Integer qux1;
	private BigDecimal qux2;
	private NestedObj nested;

	@Data
	public static class NestedObj {

		private String baz;
	}
}
