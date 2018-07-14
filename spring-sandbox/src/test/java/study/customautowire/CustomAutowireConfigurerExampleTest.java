package study.customautowire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CustomAutowireConfigurerExample.class,
        webEnvironment = WebEnvironment.NONE)
public class CustomAutowireConfigurerExampleTest {

    @Autowired
    private Bar bar;

    @Test
    public void test() {
        bar.run();
    }
}
