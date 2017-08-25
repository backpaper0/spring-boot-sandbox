package com.example.mailsample;

import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class MailSampleApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MailSampleApplication.class, args);
    }
}

@Component
class MailSample implements CommandLineRunner {

    private final MailSender mailSender;

    public MailSample(final MailSender sender) {
        this.mailSender = Objects.requireNonNull(sender);
    }

    @Override
    public void run(final String... args) throws Exception {
        final SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo("foo@example.com");
        msg.setFrom("bar@example.com");
        msg.setSubject("HELLO");
        msg.setText("Hello, world!");
        mailSender.send(msg);
    }
}