package study.validation.standard;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.util.UriComponentsBuilder;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ValidationSampleTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void test1() throws Exception {
        final URI uri = UriComponentsBuilder.fromPath("/1")
                .queryParam("foo", "FOO")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("FOO");
    }

    @Test
    public void test2() throws Exception {
        final URI uri = UriComponentsBuilder.fromPath("/1")
                .queryParam("foo", "HOGE")
                .build()
                .toUri();

        final String response = template.getForObject(uri, String.class);

        assertThat(response).isEqualTo("ERROR");
    }
}
