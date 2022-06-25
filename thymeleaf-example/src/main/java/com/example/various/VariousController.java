package com.example.various;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
		rootObj.setBar2(LocalDateTime.now());
		rootObj.setBar3(LocalTime.now());
		rootObj.setQux1(1234567890);
		rootObj.setQux2(new BigDecimal("1234567890.123"));
		RootObj.NestedObj nestedObj = new RootObj.NestedObj();
		nestedObj.setBaz("ネストされたオブジェクトのプロパティです。");
		rootObj.setNested(nestedObj);
		return rootObj;
	}
}
