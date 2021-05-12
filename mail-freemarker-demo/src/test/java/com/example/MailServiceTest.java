package com.example;

import static org.junit.Assert.*;

import javax.mail.internet.MimeMessage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;

@SpringBootTest
class MailServiceTest {

	@RegisterExtension
	static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP);

	@Autowired
	MailService sut;

	@Test
	void send() throws Exception {
		sut.send("world");
		assertTrue(greenMail.waitForIncomingEmail(5000, 1));
		MimeMessage[] messages = greenMail.getReceivedMessages();
		assertEquals("Hello, world!", GreenMailUtil.getBody(messages[0]));
	}
}
