package com.example.app.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class IndexForm {

	@NotNull(message = "{Required}")
	@Size(min = 1, message = "{Required}")
	@Size(max = 5, message = "{Size.max}")
	private String field1;
}
