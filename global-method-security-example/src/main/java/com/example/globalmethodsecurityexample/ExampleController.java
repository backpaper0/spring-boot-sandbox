package com.example.globalmethodsecurityexample;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

	@PreAuthorize("hasRole('FOO')")
	@GetMapping("/foo")
	String getFoo() {
		return "foo";
	}

	@PreAuthorize("hasAnyRole('FOO', 'BAR')")
	@GetMapping("/bar")
	String getBar() {
		return "bar";
	}

	@GetMapping("/baz")
	String getBaz() {
		return "baz";
	}
}
