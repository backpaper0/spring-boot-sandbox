package com.example.file;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.dsl.Files;

@Configuration
@EnableIntegration
public class FileFlow {

	@Bean
	public QueueChannel output() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlow
				.from(fileReadingMessageSource(), a -> a.poller(Pollers.fixedRate(100)))
				.split(Files.splitter(true).charset(StandardCharsets.UTF_8))
				.channel(output())
				.get();
	}

	@Bean
	public FileReadingMessageSource fileReadingMessageSource() {
		final FileReadingMessageSource ms = new FileReadingMessageSource();
		ms.setDirectory(new File("input"));
		return ms;
	}
}
