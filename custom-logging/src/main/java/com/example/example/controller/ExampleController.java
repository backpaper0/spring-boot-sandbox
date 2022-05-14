package com.example.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.example.common.session.NameKeeper;
import com.example.example.dto.Greeting;
import com.example.example.dto.HttpBinResponse;
import com.example.example.form.ExampleForm;

@Controller
@RequestMapping("/")
@ConfigurationProperties(prefix = "example")
public class ExampleController {

	@Autowired
	private NameKeeper exampleBean;
	@Autowired
	private RestTemplate http;

	@GetMapping
	public String index() {
		return "index";
	}

	@PostMapping("post")
	public String post(@Validated ExampleForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "index";
		}
		exampleBean.setName(form.getName());
		return "index";
	}

	@PostMapping(path = "post", params = "clear")
	public String clear() {
		exampleBean.clear();
		return "index";
	}

	@PostMapping(path = "post", params = "api")
	public String api(Model model) {

		Greeting request = new Greeting();
		request.setMessage("Hello World");

		HttpBinResponse response = http.postForObject("/delay/3", request, HttpBinResponse.class);

		model.addAttribute("apiResponse", response.toString());

		return "index";
	}

	@ModelAttribute
	public ExampleForm form() {
		return new ExampleForm();
	}
}
