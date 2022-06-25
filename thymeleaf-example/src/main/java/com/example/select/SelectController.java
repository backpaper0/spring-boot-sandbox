package com.example.select;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/select")
public class SelectController {

	@GetMapping
	public String index() {
		return "select/index";
	}

	@PostMapping
	public String post(SelectForm form) {
		System.out.println(form);
		return "select/index";
	}

	@ModelAttribute
	public SelectForm form() {
		return new SelectForm();
	}

	@ModelAttribute("options")
	public List<Option> options() {
		return List.of(
				new Option("a", "foo"),
				new Option("b", "bar"),
				new Option("c", "baz"));
	}
}
