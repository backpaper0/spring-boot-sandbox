package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import study.entity.Todo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringBootDomaStudyApplication.class)
@WebIntegrationTest
public class SpringBootDomaStudyApplicationTests {

    private final RestTemplate template = new TestRestTemplate();

    @Test
    public void testTodoList() throws Exception {
        RequestEntity<?> request = new RequestEntity<>(HttpMethod.GET,
                URI.create("http://localhost:8080/todo"));
        ResponseEntity<List<Todo>> response = template.exchange(request,
                new ParameterizedTypeReference<List<Todo>>() {
                });
        assertThat(response.getBody(), is(Collections.emptyList()));
    }
}
