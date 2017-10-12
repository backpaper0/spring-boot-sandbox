package com.example.snakecase;

import static org.assertj.core.api.Assertions.*;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class SnakecaseApplicationTests {

    @Autowired
    private TestRestTemplate template;

    @Test
    public void contextLoads() {
        final URI uri = UriComponentsBuilder.fromPath("/")
                .queryParam("foo_bar", "HELLOWORLD")
                .build()
                .toUri();
        final String response = template.getForObject(uri, String.class);
        assertThat(response).isEqualTo("HELLOWORLD");
    }
}
