package com.example.form;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/form-example")
public class FormExampleController {

	private static final String INDEX_VIEW = "form-example/index";

	@GetMapping
	public String index() {
		return INDEX_VIEW;
	}

	@PostMapping
	public String post(@Validated ExampleForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {

		System.out.println(form);

		if (bindingResult.hasErrors()) {
			return INDEX_VIEW;
		}
		redirectAttributes.addFlashAttribute("posted", true);
		return "redirect:/form-example";
	}

	@ModelAttribute
	public ExampleForm form() {
		return new ExampleForm();
	}
}
