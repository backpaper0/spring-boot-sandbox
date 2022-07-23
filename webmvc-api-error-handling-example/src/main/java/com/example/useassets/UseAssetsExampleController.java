package com.example.useassets;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("use-assets")
public class UseAssetsExampleController {

	@GetMapping
	public String index() {
		return "use-assets/index";
	}
}
