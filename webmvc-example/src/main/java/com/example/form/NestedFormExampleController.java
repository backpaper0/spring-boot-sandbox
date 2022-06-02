package com.example.form;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.NestedExampleForm.NestedExampleSubForm;

@Controller
@RequestMapping("/nested-form-example")
public class NestedFormExampleController {

	@GetMapping
	public String index(NestedExampleForm form) {
		List<NestedExampleSubForm> subs = List.of(
				new NestedExampleSubForm(),
				new NestedExampleSubForm(),
				new NestedExampleSubForm());
		form.setSubs(subs);
		return "form-example/nested.html";
	}

	@PostMapping
	public String post(@Validated NestedExampleForm form, BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "form-example/nested.html";
		}
		redirectAttributes.addFlashAttribute("post", form.toString());
		return "redirect:/nested-form-example";
	}
}
