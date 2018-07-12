package study.beanpostprocessor;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BeanPostProcessorExample.class)
public class BeanPostProcessorExampleTest {

    @Autowired
    private Bar bar;

    @Test
    public void get() throws Exception {
        final String s = bar.get();
        assertEquals("ABhelloBA", s);
    }
}
