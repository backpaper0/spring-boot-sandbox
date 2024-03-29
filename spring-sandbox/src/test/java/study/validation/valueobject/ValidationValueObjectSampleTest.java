package study.validation.valueobject;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ValidationValueObjectSampleTest {

	@Autowired
	private TestRestTemplate template;

	@Test
	void test1() throws Exception {

		final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("foo", "He");
		form.add("bar", "ll");
		form.add("baz", "o!");

		final RequestEntity<MultiValueMap<String, String>> request = RequestEntity
				.post(URI.create("/"))
				.body(form);

		final ResponseEntity<String> response = template.exchange(request, String.class);

		assertThat(response.getBody()).isEqualTo("Hello!");
	}

	@Test
	void test2() throws Exception {

		final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("foo", "123");
		form.add("bar", "12");
		form.add("baz", "123");

		final RequestEntity<MultiValueMap<String, String>> request = RequestEntity
				.post(URI.create("/"))
				.body(form);

		final ResponseEntity<String> response = template.exchange(request, String.class);

		assertThat(response.getBody()).isEqualTo("ERROR");
	}
}
