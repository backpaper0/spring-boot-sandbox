package com.example.web.hierarchicalroles;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.web.access.WebInvocationPrivilegeEvaluator;

@SpringBootTest
class RoleHierarchyTest {

	@Autowired
	WebInvocationPrivilegeEvaluator evaluator;

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
			"/foo | ROLE_A | true",
			"/bar | ROLE_A | true",
			"/baz | ROLE_A | true",
			"/qux | ROLE_A | false",

			"/foo | ROLE_B | false",
			"/bar | ROLE_B | true",
			"/baz | ROLE_B | true",
			"/qux | ROLE_B | false",

			"/foo | ROLE_C | false",
			"/bar | ROLE_C | false",
			"/baz | ROLE_C | true",
			"/qux | ROLE_C | false",

			"/foo | ROLE_D | false",
			"/bar | ROLE_D | false",
			"/baz | ROLE_D | false",
			"/qux | ROLE_D | false",
	})
	void test(String uri, String authority, boolean expected) {
		var authentication = new TestingAuthenticationToken("user", "...", authority);
		var actual = evaluator.isAllowed(uri, authentication);
		assertEquals(expected, actual);
	}
}
