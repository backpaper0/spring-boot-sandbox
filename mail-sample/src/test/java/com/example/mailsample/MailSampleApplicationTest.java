package com.example.mailsample;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

@SpringBootTest
class MailSampleApplicationTest {

	private Wiser wiser;

	@Autowired
	private MailSample mailSample;

	@Test
	void test() throws Exception {

		mailSample.sendMail();

		final List<WiserMessage> messages = wiser.getMessages();
		assertThat(messages.size()).isEqualTo(1);

		final WiserMessage message = messages.get(0);

		assertThat(message.getEnvelopeReceiver()).isEqualTo("foo@example.com");
		assertThat(message.getEnvelopeSender()).isEqualTo("bar@example.com");

		final MimeMessage msg = message.getMimeMessage();
		assertThat(msg.getSubject()).isEqualTo("HELLO");
		assertThat(msg.getContent()).asString().contains("Hello, world!");
	}

	@BeforeEach
	void startSMTPServer() {
		wiser = new Wiser();
		wiser.setPort(8025);
		wiser.start();
	}

	@AfterEach
	void stopSMTPServer() {
		wiser.stop();
	}
}
