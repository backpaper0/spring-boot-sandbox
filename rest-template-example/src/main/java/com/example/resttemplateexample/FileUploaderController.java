package com.example.resttemplateexample;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/upload")
public class FileUploaderController {

	@GetMapping
	public String index() {
		return "upload";
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Object post(@RequestParam final MultipartFile hoge) {
		try {
			return Map.of(
					"name", hoge.getName(),
					"filename", hoge.getOriginalFilename(),
					"content-type", hoge.getContentType(),
					"body", new String(hoge.getBytes()));
		} catch (final IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
