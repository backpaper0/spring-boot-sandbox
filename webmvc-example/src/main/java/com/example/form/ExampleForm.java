package com.example.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ExampleForm {

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
