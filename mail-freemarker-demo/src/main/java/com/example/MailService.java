package com.example;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Component
public class MailService {

	@Autowired
	private Configuration configuration;

	@Autowired
	private MailSender mailSender;

	public void send(String name) throws IOException, TemplateException {
		Template template = configuration.getTemplate("demo.ftl", "UTF-8");
		Object model = Map.of("name", name);
		String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

		SimpleMailMessage simpleMessage = new SimpleMailMessage();
		simpleMessage.setSubject("HELLO");
		simpleMessage.setText(text);
		simpleMessage.setFrom("from@example.com");
		simpleMessage.setTo("to@example.com");
		mailSender.send(simpleMessage);
	}
}
