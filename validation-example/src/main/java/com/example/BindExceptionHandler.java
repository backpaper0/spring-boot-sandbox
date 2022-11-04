package com.example;

import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BindExceptionHandler {

	private final MessageSource messageSource;

	public BindExceptionHandler(final MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	String handle(final BindException e, final Locale locale) {
		return e.getAllErrors().stream()
				.map(a -> messageSource.getMessage(a, locale))
				.collect(Collectors.joining("\n"));
	}
}
