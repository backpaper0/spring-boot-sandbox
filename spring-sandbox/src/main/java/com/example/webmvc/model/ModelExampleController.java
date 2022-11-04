package com.example.webmvc.model;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ModelExampleController {

	@ModelAttribute
	ModelExampleModel model() {
		return new ModelExampleModel("default");
	}

	@ModelAttribute("foo")
	ModelExampleModel foo() {
		return new ModelExampleModel("foo");
	}

	@ModelAttribute("bar")
	ModelExampleModel bar() {
		return new ModelExampleModel("bar");
	}

	@GetMapping("/")
	ModelExampleModel home(final ModelExampleModel model) {
		return model;
	}

	@GetMapping("/foobar")
	Object foobar(@ModelAttribute("foo") final ModelExampleModel foo,
			@ModelAttribute("bar") final ModelExampleModel bar) {
		return Map.of("foo", foo, "bar", bar);
	}
}
