package study.requestparam2;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class RequestParamSample2Test {

    @Autowired
    private TestRestTemplate template;

    @Test
    void test1() {
        final URI uri = UriComponentsBuilder.fromPath("/1")
                .queryParam("foo", "Hello")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("Hello");
    }

    @Test
    void test2() {
        final URI uri = UriComponentsBuilder.fromPath("/2")
                .queryParam("foo", "World")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("World");
    }
}
