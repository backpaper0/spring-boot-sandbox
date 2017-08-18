package study.entitybody;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpMessageConverterSampleTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void test() {
        final String response = template.postForObject("/", "x", String.class);
        assertThat(response).isEqualTo("OK");
    }
}
