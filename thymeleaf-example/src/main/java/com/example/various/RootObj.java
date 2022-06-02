package com.example.various;

import java.time.LocalDate;

import lombok.Data;

@Data
public class RootObj {

	private String foo;
	private LocalDate bar;
	private NestedObj nested;

	@Data
	public static class NestedObj {

		private String baz;
	}
}
