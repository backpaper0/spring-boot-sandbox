package com.example.ftp;

import java.io.File;
import java.nio.charset.StandardCharsets;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.dsl.Files;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.dsl.Ftp;

@Configuration
@EnableIntegration
@Import(FtpConfig.class)
public class FtpInputFlow {

	private final SessionFactory<FTPFile> sessionFactory;

	public FtpInputFlow(final SessionFactory<FTPFile> sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Bean
	public QueueChannel output() {
		return new QueueChannel();
	}

	@Bean
	public IntegrationFlow flow() {
		return IntegrationFlow
				.from(Ftp.inboundAdapter(sessionFactory).localDirectory(new File("output")),
						c -> c.poller(Pollers.fixedRate(100)))
				.split(Files.splitter(true).charset(StandardCharsets.UTF_8))
				.channel(output())
				.get();
	}
}
