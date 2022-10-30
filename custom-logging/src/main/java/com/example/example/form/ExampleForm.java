package com.example.example.form;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Data;

@Data
public class ExampleForm {

	@NotNull
	@Size(min = 1)
	private String name;

	private int delay = 3;
}
