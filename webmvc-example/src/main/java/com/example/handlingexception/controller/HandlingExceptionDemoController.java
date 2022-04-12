package com.example.handlingexception.controller;

import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.handlingexception.exception.BusinessException;
import com.example.handlingexception.form.HandlingExceptionForm;

@Controller
@RequestMapping("handlingexception")
public class HandlingExceptionDemoController {

	private final MessageSource messageSource;

	public HandlingExceptionDemoController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@GetMapping
	public String index() {
		return "handlingexception/index";
	}

	@PostMapping
	public String post(@Validated @ModelAttribute("handlingExceptionForm") HandlingExceptionForm form,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {
			return "handlingexception/index";
		}
		try {
			if (form.getContent().equals("x")) {
				throw new BusinessException("ERR0001");
			} else if (form.getContent().equals("y")) {
				throw new BusinessException("ERR0002", "content");
			} else if (form.getContent().equals("z")) {
				throw new RuntimeException();
			}
			redirectAttributes.addFlashAttribute("message", "MSG0001");
			return "redirect:/handlingexception";
		} catch (BusinessException exception) {
			exception.rejectTo(bindingResult, messageSource);
			return "handlingexception/index";
		}
	}

	@ModelAttribute("handlingExceptionForm")
	public HandlingExceptionForm getHandlingExceptionForm() {
		return new HandlingExceptionForm();
	}
}
