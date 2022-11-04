package com.example.locale;

import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/locale")
public class LocaleController {

	@GetMapping
	public String getLocale(final Locale locale) {
		return Stream.of(locale, LocaleContextHolder.getLocale()).map(Locale::toString)
				.collect(Collectors.joining(System.lineSeparator()));
	}
}
