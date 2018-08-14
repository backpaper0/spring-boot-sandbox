package study.beanpostprocessor;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig(BeanPostProcessorExample.class)
class BeanPostProcessorExampleTest {

    @Autowired
    private Bar bar;

    @Test
    void get() throws Exception {
        final String s = bar.get();
        assertEquals("ABhelloBA", s);
    }
}
