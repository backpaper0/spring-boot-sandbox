package com.example.example4;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;

@RestController
@RequestMapping("4")
public class MessageExampleController {

	private final MessageSource messageSource;

	public MessageExampleController(final MessageSource messageSource) {
		this.messageSource = Objects.requireNonNull(messageSource);
	}

	@PostMapping
	public List<String> post(@Valid final MessageExampleForm form,
			final BindingResult bindingResult, final Locale locale) {
		if (bindingResult.hasErrors()) {
			return bindingResult.getAllErrors().stream()
					.map(a -> messageSource.getMessage(a, locale))
					.collect(Collectors.toList());
		}
		return Collections.singletonList("Valid");
	}
}

final class MessageExampleForm {

	@Size(min = 1, max = 3)
	private String text;
	private int num;
	private ValueObject vo;

	public String getText() {
		return text;
	}

	public void setText(final String text) {
		this.text = text;
	}

	public int getNum() {
		return num;
	}

	public void setNum(final int num) {
		this.num = num;
	}

	public ValueObject getVo() {
		return vo;
	}

	public void setVo(final ValueObject vo) {
		this.vo = vo;
	}
}

final class ValueObject {

	private final String value;

	public ValueObject(final String value) {
		this.value = Objects.requireNonNull(value);
		if (value.isEmpty() || value.length() > 3) {
			throw new ValueObjectException();
		}
	}

	public String getValue() {
		return value;
	}
}

final class ValueObjectException extends RuntimeException {
}
