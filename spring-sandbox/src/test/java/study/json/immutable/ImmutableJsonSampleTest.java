package study.json.immutable;

import java.net.URI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ImmutableJsonSampleTest {

	@LocalServerPort
	private int port;

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
	void test() throws Exception {
		final URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/emp")
				.build()
				.toUri();
		final String body = "{\"id\":123,\"name\":\"uragami\",\"dept\":{\"id\":456,\"name\":\"development\"}}";
		final RequestEntity<String> request = RequestEntity.post(uri)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(body);

		final ResponseEntity<String> response = template.exchange(request, String.class);

		JSONAssert.assertEquals(body, response.getBody(), true);
	}

}
