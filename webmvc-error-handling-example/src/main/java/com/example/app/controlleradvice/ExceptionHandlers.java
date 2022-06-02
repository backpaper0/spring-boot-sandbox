package com.example.app.controlleradvice;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(basePackages = "com.example.app.controller")
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Throwable.class)
	public String handleError() {
		return "error/handled";
	}

	@Override
	protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Not found"));
	}
}
