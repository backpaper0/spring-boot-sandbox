package com.example.app.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class IndexForm {

	@NotNull(message = "{Required}")
	@Size(min = 1, message = "{Required}")
	@Size(max = 5, message = "{Size.max}")
	private String field1;
}
