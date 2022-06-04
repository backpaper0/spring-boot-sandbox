package com.example.controller;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlers {

	private final MessageSource messageSource;

	public ExceptionHandlers(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	@ExceptionHandler(BindException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Object handleBindException(BindException exception, Locale locale) {

		var globalErrors = exception.getGlobalErrors().stream()
				.map(error -> messageSource.getMessage(error, locale))
				.toList();

		var fieldErrors = exception.getFieldErrors().stream()
				.collect(Collectors.groupingBy(error -> error.getField(),
						Collectors.mapping(
								error -> messageSource.getMessage(error, locale),
								Collectors.toList())));

		return Map.of(
				"globalErrors", globalErrors,
				"fieldErrors", fieldErrors);
	}
}
