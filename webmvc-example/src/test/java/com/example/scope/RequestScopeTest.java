package com.example.scope;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

@SpringBootTest
public class RequestScopeTest {

    private MockMvcTester tester;

    @BeforeEach
    void setUp(WebApplicationContext wac) {
        tester = MockMvcTester.from(wac);
    }

    @Test
    void 同じリクエストのハンドリング中であればどこでインジェクションしても同じインスタンス(@Autowired ObjectMapper objectMapper) throws Exception {
        var contentAsString = tester.get()
                .uri("/request-scope-example")
                .exchange()
                .getResponse()
                .getContentAsString();
        var content = objectMapper.readValue(contentAsString, Map.class);
        assertEquals(content.get("direct"), content.get("via-service"));
    }

    @Test
    void 異なるリクエストに対しては異なるインスタンス(@Autowired ObjectMapper objectMapper) throws Exception {
        var content1 = objectMapper.readValue(
                tester.get()
                        .uri("/request-scope-example")
                        .exchange()
                        .getResponse()
                        .getContentAsString(),
                Map.class);
        var content2 = objectMapper.readValue(
                tester.get()
                        .uri("/request-scope-example")
                        .exchange()
                        .getResponse()
                        .getContentAsString(),
                Map.class);
        assertNotEquals(content1.get("direct"), content2.get("direct"));
    }
}
