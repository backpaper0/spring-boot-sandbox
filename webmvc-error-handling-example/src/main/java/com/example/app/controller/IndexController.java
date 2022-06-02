package com.example.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.app.exception.HandlingException;
import com.example.app.form.IndexForm;

@Controller
@RequestMapping("/")
public class IndexController {

	@GetMapping
	public String index(IndexForm form) {
		return "index";
	}

	@PostMapping
	public String post(@Validated IndexForm form, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "index";
		}
		redirectAttributes.addFlashAttribute("message", "MSG0001");
		return "redirect:/";
	}

	@GetMapping("handlingException")
	public String handlingException() {
		throw new HandlingException();
	}

	@GetMapping("unhandlingException")
	public String unhandlingException() {
		throw new RuntimeException();
	}
}
