package com.example.example2;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 *
 * @see org.springframework.validation.beanvalidation.SpringValidatorAdapter
 */
@RestController
@RequestMapping("/2")
public class BeanValidationExampleController {

	@PostMapping
	public String post(@Valid final ExampleForm form) {
		return "Valid";
	}
}

class ExampleForm {

	@NotNull
	@Size(min = 1)
	private String text;

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}
}
