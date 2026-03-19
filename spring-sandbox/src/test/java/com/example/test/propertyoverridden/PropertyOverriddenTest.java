package com.example.test.propertyoverridden;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class PropertyOverriddenTest {

    @Autowired
    private PropertyOverriddenApp app;

    @DynamicPropertySource
    static void props(final DynamicPropertyRegistry registry) {
        registry.add("foo.bar.baz", () -> "Yeah!");
    }

    @Test
    void test() throws Exception {
        assertEquals("Yeah!", app.getProp());
    }
}
