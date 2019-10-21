package study.entitybody;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class HttpMessageConverterSampleTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    void test() {
        final String response = template.postForObject("/", "x", String.class);
        assertThat(response).isEqualTo("OK");
    }
}
