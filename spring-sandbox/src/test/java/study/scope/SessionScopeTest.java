package study.scope;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SessionScopeTest {

	@Autowired
	private TestRestTemplate template;

	@Test
	void test() {
		final int count = template.getForObject("/demo", Integer.class);
		assertEquals(0, count);
	}
}
