package com.example.handlingexception.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlingExceptionAdvice {

	@ExceptionHandler(Throwable.class)
	public String handle(Throwable t) {
		return "handlingexception/exception-handler";
	}
}
