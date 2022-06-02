package com.example.form;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class NestedExampleForm {

	@Valid
	private List<NestedExampleSubForm> subs;

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
