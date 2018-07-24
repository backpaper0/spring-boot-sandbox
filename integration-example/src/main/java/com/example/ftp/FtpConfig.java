package com.example.ftp;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;

@Configuration
public class FtpConfig {

    @Bean
    public DefaultFtpSessionFactory defaultFtpSessionFactory() {
        final DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
        sessionFactory.setHost("localhost");
        sessionFactory.setPort(10021);
        sessionFactory.setUsername("example");
        sessionFactory.setPassword("example");
        sessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
        return sessionFactory;
    }
}
