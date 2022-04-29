package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExampleController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/page1")
	public String page1(Model model) {
		model.addAttribute("title", "Page 1");
		return "page123";
	}

	@GetMapping("/page2")
	public String page2(Model model) {
		model.addAttribute("title", "Page 2");
		return "page123";
	}

	@GetMapping("/page3")
	public String page3(Model model) {
		model.addAttribute("title", "Page 3");
		return "page123";
	}

	@GetMapping("/page5")
	public String page5() {
		throw new RuntimeException();
	}
}
