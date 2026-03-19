package study.validation.valueobject;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class ValidationValueObjectSampleTest {

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

        final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("foo", "He");
        form.add("bar", "ll");
        form.add("baz", "o!");

        final RequestEntity<MultiValueMap<String, String>> request =
                RequestEntity.post("http://localhost:" + port + "/").body(form);

        final ResponseEntity<String> response = template.exchange(request, String.class);

        assertThat(response.getBody()).isEqualTo("Hello!");
    }

    @Test
    void test2() throws Exception {

        final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("foo", "123");
        form.add("bar", "12");
        form.add("baz", "123");

        final RequestEntity<MultiValueMap<String, String>> request =
                RequestEntity.post("http://localhost:" + port + "/").body(form);

        final ResponseEntity<String> response = template.exchange(request, String.class);

        assertThat(response.getBody()).isEqualTo("ERROR");
    }
}
