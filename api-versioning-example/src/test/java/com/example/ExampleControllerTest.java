package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@SpringBootTest
@AutoConfigureMockMvc
public class ExampleControllerTest {

    @Autowired
    private MockMvcTester tester;

    @Test
    void test() {
        tester.get()
                .uri("/")
                .assertThat()
                .bodyJson()
                .hasPathSatisfying("$.version", a -> a.assertThat().isEqualTo("default"));
    }
}
