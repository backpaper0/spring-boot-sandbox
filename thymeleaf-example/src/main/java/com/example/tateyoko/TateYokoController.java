package com.example.tateyoko;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tate-yoko")
public class TateYokoController {

	@GetMapping
	public String index(Model model) {

		List<TateYokoDto> list = List.of(
				new TateYokoDto(1, "foo", "a"),
				new TateYokoDto(2, "bar", "b"),
				new TateYokoDto(3, "baz", "c"),
				new TateYokoDto(4, "qux", "d"));
		model.addAttribute("list", list);

		return "tate-yoko/index";
	}
}
