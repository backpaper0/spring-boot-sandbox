package com.example.textarea;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/textarea")
public class TextareaController {

	@GetMapping
	public String index() {
		return "textarea/index";
	}

	@PostMapping
	public String post(TextareaForm form) {
		System.out.println(form);
		return "textarea/index";
	}

	@ModelAttribute
	public TextareaForm form() {
		return new TextareaForm();
	}
}
