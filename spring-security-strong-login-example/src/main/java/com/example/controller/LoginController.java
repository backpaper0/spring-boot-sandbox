package com.example.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {

	@GetMapping
	public String index(Authentication authentication) {
		if (authentication != null) {
			return "redirect:/";
		}
		return "login";
	}
}
