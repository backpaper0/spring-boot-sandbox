package study.publicfield;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PublicFieldSampleTest {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void test() throws Exception {
        final MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("foo", "HELLO");
        form.add("bar", "123");
        form.add("baz", "true");

        final RequestEntity<MultiValueMap<String, String>> request = RequestEntity
                .post(URI.create("/sample"))
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(form);

        final ResponseEntity<String> response = template.exchange(request, String.class);
        assertEquals("HELLO:123:true", response.getBody());
    }
}
