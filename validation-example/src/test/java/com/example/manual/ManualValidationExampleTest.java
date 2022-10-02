package com.example.manual;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Locale;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

import com.example.manual.ManualValidationExampleTest.AppValidator;

@SpringBootTest
@Import(AppValidator.class)
public class ManualValidationExampleTest {

	@Autowired
	SmartValidator smartValidator;
	@Autowired
	AppValidator appValidator;

	@Test
	void testManualValidation() {
		Object target = new TestObj();
		Errors errors = new BeanPropertyBindingResult(target, "target");
		smartValidator.validate(target, errors);

		assertEquals(true, errors.hasFieldErrors("field1"));

		FieldError fieldError = errors.getFieldError("field1");
		assertEquals("NotNull", fieldError.getCode());
	}

	@Test
	void testAppValidator() {
		Object target = new TestObj();
		RuntimeException exception = assertThrows(RuntimeException.class, () -> appValidator.validate(target));
		System.out.println(exception.getMessage());
	}

	public static class TestObj {
		@NotNull
		private String field1;
		@NotNull
		private String field2;
		@NotNull
		private String field3;
	}

	@TestComponent
	public static class AppValidator {

		@Autowired
		private SmartValidator smartValidator;
		@Autowired
		private MessageSource messageSource;

		public void validate(Object target) {
			Errors errors = new BeanPropertyBindingResult(target, "target");
			smartValidator.validate(target, errors);
			if (errors.hasErrors()) {
				Locale locale = Locale.getDefault();
				String message = errors.getAllErrors().stream()
						.map(error -> {
							if (error instanceof FieldError) {
								String field = ((FieldError) error).getField();
								return field + ": " + messageSource.getMessage(error, locale);
							}
							return messageSource.getMessage(error, locale);
						})
						.collect(Collectors.joining(System.lineSeparator()));
				throw new RuntimeException(message);
			}
		}
	}
}
