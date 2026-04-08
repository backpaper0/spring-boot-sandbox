package com.example.example5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class SpringValidatorAndBeanValidationExampleControllerTest {

    private MockMvcTester tester;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        tester = MockMvcTester.from(wac);
    }

    @Test
    void test() throws Exception {
        tester.post()
                .uri("/5")
                .content("{}")
                .contentType(MediaType.APPLICATION_JSON)
                .assertThat()
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson()
                .hasPathSatisfying("$.fieldErrors.foo[0]", a -> a.assertThat().isEqualTo("must not be empty"))
                .hasPathSatisfying("$.fieldErrors.bar[0]", a -> a.assertThat().isEqualTo("must not be blank"))
                .hasPathSatisfying("$.globalErrors[0]", a -> a.assertThat().isEqualTo("baz error"));
    }
}
