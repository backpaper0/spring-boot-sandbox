package study.validation.standard;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ValidationSampleTest {

	@Autowired
	private TestRestTemplate template;

	@Test
	void test1() throws Exception {
		final URI uri = UriComponentsBuilder.fromPath("/1")
				.queryParam("foo", "FOO")
				.build()
				.toUri();

		final String response = template.getForObject(uri, String.class);

		assertThat(response).isEqualTo("FOO");
	}

	@Test
	void test2() throws Exception {
		final URI uri = UriComponentsBuilder.fromPath("/1")
				.queryParam("foo", "HOGE")
				.build()
				.toUri();

		final String response = template.getForObject(uri, String.class);

		assertThat(response).isEqualTo("ERROR");
	}
}
