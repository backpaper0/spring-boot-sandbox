package com.example.json;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "com.example.json.masking-targets=bar")
public class JsonParseAndMaskingExampleTest {

	@Autowired
	JsonParseAndMaskingExample sut;

	@Test
	void test() throws Exception {
		String actual = sut.mask("""
				{
				"foo":"hello\\nworld",
				"bar":123,
				"baz":[1, 2, 3],
				"qux": {"foo":true,"bar":456,"baz":false,"qux":null},
				"abc": {"bar":[1,2,{"foo":"xxx","bar":"yyy"}]}
				}
				""");
		String expected = "{\"foo\":\"helloworld\","
				+ "\"bar\":********,"
				+ "\"baz\":[1,2,3],"
				+ "\"qux\":{\"foo\":true,\"bar\":********,\"baz\":false,\"qux\":null},"
				+ "\"abc\":{\"bar\":[1,2{\"foo\":\"xxx\",\"bar\":\"********\"}]}}";
		assertEquals(expected, actual);
	}

	@Test
	void testInvalidJson() throws Exception {
		String actual = sut.mask("""
				"bar": "invalid"
				""");
		String expected = "<parse error>";
		assertEquals(expected, actual);
	}
}
