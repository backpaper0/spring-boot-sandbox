package com.example.mock.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.annotation.AliasFor;

@ConditionalOnProperty
@Retention(RetentionPolicy.RUNTIME)
public @interface MyComponentImpl {

	@AliasFor(annotation = ConditionalOnProperty.class, attribute = "name")
	String name();

	@AliasFor(annotation = ConditionalOnProperty.class, attribute = "havingValue")
	String havingValue();
}
