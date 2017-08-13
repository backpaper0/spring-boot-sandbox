package study.publicfield;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
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
        assertThat(response.getBody(), is("HELLO:123:true"));
    }
}
