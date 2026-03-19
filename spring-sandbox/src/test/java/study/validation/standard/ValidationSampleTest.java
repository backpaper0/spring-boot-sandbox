package study.validation.standard;

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
class ValidationSampleTest {

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
    void test1() throws Exception {
        final URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/1")
                .queryParam("foo", "FOO")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("FOO");
    }

    @Test
    void test2() throws Exception {
        final URI uri = UriComponentsBuilder.fromUriString("http://localhost:" + port + "/1")
                .queryParam("foo", "HOGE")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("ERROR");
    }
}
