package com.example.form;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class NestedExampleForm {

	@Valid
	private List<NestedExampleSubForm> subs = new ArrayList<>();

	@Data
	public static class NestedExampleSubForm {

		@NotNull(message = "{Required}")
		@Size(min = 1, message = "{Required}")
		private String field1;

		@NotNull(message = "{Required}")
		@Size(min = 1, message = "{Required}")
		private String field2;

		@NotNull(message = "{Required}")
		@Size(min = 1, message = "{Required}")
		private String field3;

		private boolean field4;
	}
}
