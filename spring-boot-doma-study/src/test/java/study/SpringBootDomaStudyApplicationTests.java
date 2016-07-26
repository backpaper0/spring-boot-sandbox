package study;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import study.entity.Todo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SpringBootDomaStudyApplicationTests {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void testTodoList() throws Exception {
        ResponseEntity<List<Todo>> response = template.exchange(
                "/todo",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Todo>>() {
                });
        assertThat(response.getBody(), is(Collections.emptyList()));
    }
}
