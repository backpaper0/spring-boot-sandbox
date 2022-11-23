package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import jakarta.validation.ValidationException;

@SpringBootApplication
@Controller
public class DemoApplication {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/")
	String home() {
		return "index";
	}

	@PostMapping("/")
	@ResponseBody
	ResponseEntity<StreamingResponseBody> post(@RequestPart final MultipartFile file)
			throws Exception {

		if (file.isEmpty()) {
			throw new ValidationException("file was empty");
		}

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("filename", "sample.txt");

		final StreamingResponseBody body = out -> out.write(file.getBytes());

		return ResponseEntity.ok()
				.headers(headers)
				.contentType(MediaType.valueOf("application/force-download"))
				.body(body);
	}

	@ExceptionHandler
	String handle(final Exception e, final Model model) {
		e.printStackTrace(System.out);
		model.addAttribute("errorMessage", e.getMessage());
		return "index";
	}
}
