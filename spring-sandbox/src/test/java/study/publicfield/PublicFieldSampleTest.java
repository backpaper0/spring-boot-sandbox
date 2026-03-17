package study.publicfield;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class PublicFieldSampleTest {

	@LocalServerPort
	int port;

	RestTemplate template;

	@BeforeEach
	void setup() {
		template = new RestTemplate();
		template.setErrorHandler(new DefaultResponseErrorHandler() {
			@Override
			public boolean hasError(ClientHttpResponse response) {
				return false;
			}
		});
	}

	@Test
	public void test() throws Exception {
		final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
		form.add("foo", "HELLO");
		form.add("bar", "123");
		form.add("baz", "true");

		final RequestEntity<MultiValueMap<String, String>> request = RequestEntity
				.post("http://localhost:" + port + "/sample")
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.body(form);

		final ResponseEntity<String> response = template.exchange(request, String.class);
		assertEquals("HELLO:123:true", response.getBody());
	}
}
