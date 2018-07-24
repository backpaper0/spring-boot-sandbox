package com.example.ftp;

import org.apache.commons.net.ftp.FTPFile;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.file.remote.session.SessionFactory;
import org.springframework.integration.ftp.dsl.Ftp;

@Configuration
@EnableIntegration
@Import(FtpConfig.class)
public class FtpOutputFlow {

    private final SessionFactory<FTPFile> sessionFactory;

    public FtpOutputFlow(final SessionFactory<FTPFile> sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Bean
    public DirectChannel input() {
        return new DirectChannel();
    }

    @Bean
    public QueueChannel output() {
        return new QueueChannel();
    }

    @Bean
    public IntegrationFlow flow() {
        return IntegrationFlows
                .from(input())
                .handle(Ftp.outboundAdapter(sessionFactory)
                        .remoteDirectory("hoge")
                        .autoCreateDirectory(true))
                .get();
    }
}
