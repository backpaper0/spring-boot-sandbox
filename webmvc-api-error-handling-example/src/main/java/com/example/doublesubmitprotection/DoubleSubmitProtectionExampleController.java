package com.example.doublesubmitprotection;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("double-submit-protection")
public class DoubleSubmitProtectionExampleController {

	@GetMapping
	public String index() {
		return "double-submit-protection/index";
	}

	@PostMapping(params = "submit1")
	public String submit1() {
		System.out.println("submit1");
		return "redirect:/double-submit-protection";
	}

	@PostMapping(params = "submit2")
	public String submit2() {
		System.out.println("submit2");
		return "redirect:/double-submit-protection";
	}
}
