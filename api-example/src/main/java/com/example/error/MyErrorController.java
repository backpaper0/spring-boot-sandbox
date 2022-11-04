package com.example.error;

import java.util.Map;

import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/error")
public class MyErrorController implements ErrorController {

	private final ErrorAttributes errorAttributes;

	public MyErrorController(ErrorAttributes errorAttributes) {
		this.errorAttributes = errorAttributes;
	}

	@RequestMapping
	public Object error(WebRequest webRequest, HttpServletResponse response) {
		Throwable error = errorAttributes.getError(webRequest);
		System.out.println(error);
		String message = getMessage(response.getStatus());
		return Map.of("message", message);
	}

	private static String getMessage(int status) {
		switch (status) {
		case 404:
			return "Not Found";
		case 405:
			return "Method Not Allowed";
		case 500:
			return "Internal Server Error";
		default:
			return "Error";
		}
	}
}
