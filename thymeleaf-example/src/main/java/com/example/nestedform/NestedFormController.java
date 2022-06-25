package com.example.nestedform;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.nestedform.NestedFormForm.SubForm;

@Controller
@RequestMapping("/nested-form")
public class NestedFormController {

	@GetMapping
	public String index(NestedFormForm form) {
		form.getSubForms().add(new SubForm());
		form.getSubForms().add(new SubForm());
		form.getSubForms().add(new SubForm());
		return "nested-form/index";
	}

	@PostMapping
	public String post(@Validated NestedFormForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "nested-form/index";
		}
		System.out.println(form);
		return "nested-form/index";
	}

	//	@ModelAttribute
	//	public NestedFormForm form() {
	//		return new NestedFormForm();
	//	}

	@ModelAttribute("values")
	public List<String> values() {
		return List.of("foo", "bar", "baz");
	}
}
