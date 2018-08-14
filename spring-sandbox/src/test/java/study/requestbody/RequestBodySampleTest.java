package study.requestbody;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestBodySampleTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void test() {
        final RequestEntity<String> request = RequestEntity.post(URI.create("/"))
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"value\":\"Hello, world!\"}");

        final String response = template.exchange(request, String.class).getBody();

        assertThat(response).isEqualTo("Hello, world!");
    }
}
