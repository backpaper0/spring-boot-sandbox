package com.example.various;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/various")
public class VariousController {

	@GetMapping
	public String index() {
		return "various/index";
	}

	@ModelAttribute
	public RootObj rootObj() {
		RootObj rootObj = new RootObj();
		rootObj.setFoo("プロパティです。");
		rootObj.setBar(LocalDate.now());
		RootObj.NestedObj nestedObj = new RootObj.NestedObj();
		nestedObj.setBaz("ネストされたオブジェクトのプロパティです。");
		rootObj.setNested(nestedObj);
		return rootObj;
	}
}
