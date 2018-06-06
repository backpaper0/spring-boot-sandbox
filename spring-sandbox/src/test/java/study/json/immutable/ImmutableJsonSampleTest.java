package study.json.immutable;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ImmutableJsonSampleTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate template;

    @Test
    public void test() {
        final URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/emp")
                .build()
                .toUri();
        final String body = "{\"id\":123,\"name\":\"uragami\",\"dept\":{\"id\":456,\"name\":\"development\"}}";
        final RequestEntity<String> request = RequestEntity.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .body(body);

        final ResponseEntity<String> response = template.exchange(request, String.class);

        System.out.println(response.getBody());
    }

}
