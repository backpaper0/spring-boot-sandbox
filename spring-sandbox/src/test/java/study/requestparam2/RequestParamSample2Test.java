package study.requestparam2;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RequestParamSample2Test {

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
    void test1() {
        final URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/1")
                .queryParam("foo", "Hello")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("Hello");
    }

    @Test
    void test2() {
        final URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/2")
                .queryParam("foo", "World")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("World");
    }
}
