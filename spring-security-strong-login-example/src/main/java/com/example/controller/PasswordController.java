package com.example.controller;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.form.PasswordForm;
import com.example.service.PasswordService;

@Controller
@RequestMapping("/password")
public class PasswordController {

	private final PasswordService passwordService;

	public PasswordController(PasswordService passwordService) {
		this.passwordService = passwordService;
	}

	@GetMapping
	public String index() {
		return "password";
	}

	@PostMapping
	public String changePassword(@ModelAttribute("passwordForm") @Validated PasswordForm form,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {

		if (bindingResult.hasErrors()) {
			form.setCurrentPassword(null);
			form.setNewPassword(null);
			form.setConfirmPassword(null);
			return "password";
		}

		try {
			passwordService.changePassword(form.getCurrentPassword(), form.getNewPassword());
		} catch (BadCredentialsException e) {
			bindingResult.rejectValue("currentPassword", "mistakePassword");
			form.setCurrentPassword(null);
			form.setNewPassword(null);
			form.setConfirmPassword(null);
			return "password";
		}

		redirectAttributes.addFlashAttribute("message", "passwordChanged");

		return "redirect:/password";
	}

	@ModelAttribute("passwordForm")
	public PasswordForm form() {
		return new PasswordForm();
	}
}
