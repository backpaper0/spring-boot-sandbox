package com.example.nestedform;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NestedFormForm {

	@Valid
	private List<SubForm> subForms = new ArrayList<>();

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SubForm {

		@Size(max = 3)
		private String foo;
		@Size(max = 3)
		private String bar;
	}
}
