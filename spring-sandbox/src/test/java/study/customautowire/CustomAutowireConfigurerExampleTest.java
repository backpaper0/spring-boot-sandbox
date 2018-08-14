package study.customautowire;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(CustomAutowireConfigurerExample.class)
class CustomAutowireConfigurerExampleTest {

    @Autowired
    private Bar bar;

    @Test
    void test() {
        assertEquals(Arrays.asList("foo 1", "foo 2"), bar.get());
    }
}
