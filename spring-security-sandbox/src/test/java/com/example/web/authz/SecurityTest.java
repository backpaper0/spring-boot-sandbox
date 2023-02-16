package com.example.web.authz;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

@SpringBootTest
class SecurityTest {

	@Autowired
	WebInvocationPrivilegeEvaluator evaluator;

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
			"/foo          | FOO  | true ",
			"/foo/         | FOO  | false",
			"/foo.html     | FOO  | false",
			"/foo/page1    | FOO  | false",
			"/foo/page2    | FOO  | false",
			"/foo          | BAR  | false",

			"/bar          | BAR  | true ",
			"/bar/         | BAR  | true ",
			"/bar.html     | BAR  | false",
			"/bar/page1    | BAR  | true ",
			"/bar/page2    | BAR  | true ",
			"/bar          | FOO  | false",

			"/baz          | BAZ1 | true ",
			"/baz/         | BAZ1 | false",
			"/baz.html     | BAZ1 | false",
			"/baz/page1    | BAZ1 | false",
			"/baz/page2    | BAZ1 | false",
			"/baz          | BAZ2 | true ",
			"/baz/         | BAZ2 | false",
			"/baz.html     | BAZ2 | false",
			"/baz/page1    | BAZ2 | false",
			"/baz/page2    | BAZ2 | false",
			"/baz          | FOO  | false",

			"/qux          | FOO  | true ",
			"/qux          | BAR  | true ",
			"/qux          | BAZ1 | true ",
			"/qux          | BAZ2 | true ",
	})
	void test(String uri, String authority, boolean expected) {
		Authentication authentication = new TestingAuthenticationToken(
				"user1", "...", authority);
		boolean actual = evaluator.isAllowed(uri, authentication);
		assertEquals(expected, actual);
	}

}
