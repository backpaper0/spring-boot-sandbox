package com.example;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.TimeZone;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ExampleRunner implements ApplicationRunner {

	@Override
	public void run(ApplicationArguments args) throws Exception {
		Locale locale = Locale.getDefault();
		TimeZone timeZone = TimeZone.getDefault();
		Charset charset = Charset.defaultCharset();

		System.out.printf("locale: %s%n", locale);
		System.out.printf("timeZone: %s%n", timeZone);
		System.out.printf("LocalDateTime#now: %s%n", LocalDateTime.now());
		System.out.printf("charset: %s%n", charset);
	}
}
