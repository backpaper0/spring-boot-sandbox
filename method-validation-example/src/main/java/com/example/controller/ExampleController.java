package com.example.controller;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.ConstraintViolationException;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("/example")
// クラスに@Validatedを付ける
@Validated
public class ExampleController {

	@PostMapping
	public Object post(
			// メソッドの引数に制約アノテーションを付ける
			@RequestParam @Size(max = 3) String msg) {
		return Map.of("msg", msg);
	}

	// バリデーションエラーがあるとConstraintViolationExceptionがスローされる
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	public Object handle(ConstraintViolationException exception, Locale locale) {
		return exception.getConstraintViolations().stream()
				.collect(Collectors.groupingBy(a -> a.getPropertyPath().toString(),
						Collectors.mapping(a -> a.getMessage(), Collectors.toList())));
	}
}
