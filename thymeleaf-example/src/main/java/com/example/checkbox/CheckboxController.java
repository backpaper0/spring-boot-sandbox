package com.example.checkbox;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/checkbox")
public class CheckboxController {

	@GetMapping
	public String index() {
		return "checkbox/index";
	}

	@PostMapping
	public String post(CheckboxForm form) {
		System.out.println(form);
		return "checkbox/index";
	}

	@ModelAttribute
	public CheckboxForm form() {
		return new CheckboxForm();
	}

	@ModelAttribute("values")
	public List<String> values() {
		return List.of("foo", "bar", "baz");
	}
}
