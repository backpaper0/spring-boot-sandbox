package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public abstract class RestControllerTestBase {

	protected String read(String path) {
		String prefix = getClass().getName().replace('.', '/');
		Resource resource = new ClassPathResource(prefix + "/" + path);
		try (InputStream in = resource.getInputStream()) {
			return new String(in.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
