package com.example.mailsample;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.mail.internet.MimeMessage;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailSampleApplicationTest {

    private static Wiser wiser;

    @Test
    public void test() throws Exception {
        final List<WiserMessage> messages = wiser.getMessages();
        assertThat(messages.size()).isEqualTo(1);

        final WiserMessage message = messages.get(0);

        assertThat(message.getEnvelopeReceiver()).isEqualTo("foo@example.com");
        assertThat(message.getEnvelopeSender()).isEqualTo("bar@example.com");

        final MimeMessage msg = message.getMimeMessage();
        assertThat(msg.getSubject()).isEqualTo("HELLO");
        assertThat(msg.getContent()).asString().contains("Hello, world!");
    }

    @BeforeClass
    public static void startSMTPServer() {
        wiser = new Wiser();
        wiser.setPort(8025);
        wiser.start();
    }

    @AfterClass
    public static void stopSMTPServer() {
        wiser.stop();
    }
}
