package com.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;

@SpringBootTest
public class MessageTest {

	@Autowired
	MessageSource messages;

	@Test
	void test() {
		assertEquals("foo", messages.getMessage("message1", null, null));
		assertEquals("bar", messages.getMessage("message2", null, null));
		assertEquals("baz", messages.getMessage("message3", null, null));
		// キーが重複しているとspring.messages.basenameに書いた順で最も先頭のメッセージソースになる感じ
		assertEquals("barbar", messages.getMessage("message4", null, null));
	}
}
