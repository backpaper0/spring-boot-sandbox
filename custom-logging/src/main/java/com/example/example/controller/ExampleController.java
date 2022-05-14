package com.example.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

	@PostMapping
	public String post(@Validated ExampleForm form, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "index";
		}
		exampleBean.setName(form.getName());
		return "redirect:/";
	}

	@PostMapping(params = "clear")
	public String clear() {
		exampleBean.clear();
		return "redirect:/";
	}

	@PostMapping(params = "api")
	public String api(ExampleForm form, RedirectAttributes redirectAttributes) {

		Greeting request = new Greeting();
		request.setMessage("Hello World");

		int delay = form.getDelay();
		delay = Math.max(delay, 1);
		delay = Math.min(delay, 10);
		HttpBinResponse response = http.postForObject("/delay/" + delay, request, HttpBinResponse.class);

		redirectAttributes.addFlashAttribute("apiResponse", response.toString());

		return "redirect:/";
	}

	@ModelAttribute
	public ExampleForm form() {
		return new ExampleForm();
	}
}
