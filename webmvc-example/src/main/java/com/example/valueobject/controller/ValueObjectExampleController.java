package com.example.valueobject.controller;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.valueobject.entity.Task;

@RestController
@RequestMapping("valueobjects")
public class ValueObjectExampleController {

	@Autowired
	private MessageSource messageSource;

	@PostMapping
	public Object create(@ModelAttribute("taskForm") Task form) {
		return form;
	}

	@ExceptionHandler(BindException.class)
	public Object handle(BindException exception, Locale locale) {

		List<String> globalErrors = exception.getGlobalErrors().stream()
				.map(a -> messageSource.getMessage(a, locale))
				.toList();

		Map<String, List<String>> fieldErrors = exception.getFieldErrors().stream()
				.collect(Collectors.groupingBy(a -> a.getField(),
						Collectors.mapping(a -> messageSource.getMessage(a, locale), Collectors.toList())));

		return Map.of("globalErrors", globalErrors, "fieldErrors", fieldErrors);
	}
}
