package com.example.databinder;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.convert.ConversionService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;

@SpringBootTest
public class DataBinderExampleTest {

	@Autowired
	private ConversionService conversionService;
	@Autowired
	private SpringValidatorAdapter validator;

	private TargetExample target;
	private DataBinder binder;

	@BeforeEach
	void init() {
		target = new TargetExample();
		binder = new DataBinder(target);
		binder.setConversionService(conversionService);
		binder.addValidators(validator);
	}

	@Test
	void test() {
		binder.bind(new MutablePropertyValues(Map.of(
				"field1", "hello",
				"field2", "123",
				"field3", "456",
				"field4", "789",
				"field5", "2022-04-20T06:00:00",
				"field6", "2022-04-20",
				"field7", "06:00:00",
				"field8", "true")));
		binder.validate();

		assertEquals("hello", target.getField1());
		assertEquals(123, target.getField2());
		assertEquals(456L, target.getField3());
		assertEquals(new BigDecimal("789"), target.getField4());
		assertEquals(LocalDateTime.parse("2022-04-20T06:00:00"), target.getField5());
		assertEquals(LocalDate.parse("2022-04-20"), target.getField6());
		assertEquals(LocalTime.parse("06:00:00"), target.getField7());
		assertEquals(true, target.isField8());
	}

	@Test
	void testTypeConversionFailure() {
		binder.bind(new MutablePropertyValues(Map.of(
				"field1", "hello",
				"field2", "hello",
				"field3", "hello",
				"field4", "hello",
				"field5", "hello",
				"field6", "hello",
				"field7", "hello",
				"field8", "hello")));
		binder.validate();

		BindingResult bindingResult = binder.getBindingResult();
		assertFalse(bindingResult.hasFieldErrors("field1"));

		assertArrayEquals(new String[] {
				"typeMismatch.target.field2",
				"typeMismatch.field2",
				"typeMismatch.java.lang.Integer",
				"typeMismatch" },
				bindingResult.getFieldError("field2").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field3",
				"typeMismatch.field3",
				"typeMismatch.java.lang.Long",
				"typeMismatch" },
				bindingResult.getFieldError("field3").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field4",
				"typeMismatch.field4",
				"typeMismatch.java.math.BigDecimal",
				"typeMismatch" },
				bindingResult.getFieldError("field4").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field5",
				"typeMismatch.field5",
				"typeMismatch.java.time.LocalDateTime",
				"typeMismatch" },
				bindingResult.getFieldError("field5").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field6",
				"typeMismatch.field6",
				"typeMismatch.java.time.LocalDate",
				"typeMismatch" },
				bindingResult.getFieldError("field6").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field7",
				"typeMismatch.field7",
				"typeMismatch.java.time.LocalTime",
				"typeMismatch" },
				bindingResult.getFieldError("field7").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field8",
				"typeMismatch.field8",
				"typeMismatch.boolean",
				"typeMismatch" },
				bindingResult.getFieldError("field8").getCodes());
	}

	@Test
	void testBeanValidation() {
		binder.bind(new MutablePropertyValues(Map.of(
				//"field1", "hello",
				"field2", "1000",
				"field3", "1001")));
		binder.validate();

		BindingResult bindingResult = binder.getBindingResult();

		assertArrayEquals(new String[] {
				"NotNull.target.field1",
				"NotNull.field1",
				"NotNull.java.lang.String",
				"NotNull" },
				bindingResult.getFieldError("field1").getCodes());

		assertFalse(bindingResult.hasFieldErrors("field2"));

		assertArrayEquals(new String[] {
				"Max.target.field3",
				"Max.field3",
				"Max.java.lang.Long",
				"Max" },
				bindingResult.getFieldError("field3").getCodes());
	}

	@Test
	void testTypeConversionAndBeanValidation() {
		binder.bind(new MutablePropertyValues(Map.of(
				//"field1", "hello",
				"field2", "1000",
				"field3", "1001",
				"field4", "hello")));
		binder.validate();

		BindingResult bindingResult = binder.getBindingResult();

		assertArrayEquals(new String[] {
				"NotNull.target.field1",
				"NotNull.field1",
				"NotNull.java.lang.String",
				"NotNull" },
				bindingResult.getFieldError("field1").getCodes());

		assertFalse(bindingResult.hasFieldErrors("field2"));

		assertArrayEquals(new String[] {
				"Max.target.field3",
				"Max.field3",
				"Max.java.lang.Long",
				"Max" },
				bindingResult.getFieldError("field3").getCodes());

		assertArrayEquals(new String[] {
				"typeMismatch.target.field4",
				"typeMismatch.field4",
				"typeMismatch.java.math.BigDecimal",
				"typeMismatch" },
				bindingResult.getFieldError("field4").getCodes());
	}
}
