package com.example.buildin;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.SmartValidator;

import jakarta.validation.constraints.Email;

@SpringBootTest
class EmailConstraintTest {

	@Autowired
	SmartValidator validator;

	@Test
	void invalid() throws Exception {
		TestObject target = new TestObject();
		target.myEmail = "foobar*example.com";

		Errors errors = new BeanPropertyBindingResult(target, "target");
		validator.validate(target, errors);

		assertTrue(errors.hasFieldErrors("myEmail"));
		List<FieldError> fieldErrors = errors.getFieldErrors("myEmail");
		assertEquals(1, fieldErrors.size());
		assertEquals("Email", fieldErrors.get(0).getCode());
	}

	@Test
	void valid() throws Exception {
		TestObject target = new TestObject();
		target.myEmail = "foobar@example.com";

		Errors errors = new BeanPropertyBindingResult(target, "target");
		validator.validate(target, errors);

		assertFalse(errors.hasFieldErrors("myEmail"));
	}

	static class TestObject {

		// バリデーターの実装クラスは
		// org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator
		@Email
		String myEmail;
	}
}
