package study.requestbody;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RequestBodySampleTest {

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
	void test() {
		final RequestEntity<String> request = RequestEntity.post("http://localhost:" + port + "/")
				.contentType(MediaType.APPLICATION_JSON)
				.body("{\"value\":\"Hello, world!\"}");

		final String response = template.exchange(request, String.class).getBody();

		assertThat(response).isEqualTo("Hello, world!");
	}
}
