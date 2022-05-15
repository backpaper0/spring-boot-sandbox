package com.example.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.example.example.dto.ExampleCriteriaDto;
import com.example.example.dto.ExampleDto;
import com.example.example.form.ExampleForm;
import com.example.example.service.ExampleService;

@Controller
@RequestMapping("/")
@SessionAttributes(types = ExampleForm.class)
public class ExampleController {

	@Autowired
	private ExampleService service;

	@GetMapping
	public String index(SessionStatus sessionStatus) {
		sessionStatus.setComplete();
		return "example/search/index";
	}

	@GetMapping("search")
	public String search(ExampleForm form, @PageableDefault(size = 10) Pageable pageable, Model model) {
		ExampleCriteriaDto dto = form.toExampleCriteriaDto();
		dto.setPageable(pageable);

		Page<ExampleDto> page = service.selectExamples(dto);
		model.addAttribute("page", page);

		return "example/search/index";
	}

	@ModelAttribute
	public ExampleForm form() {
		return new ExampleForm();
	}
}
