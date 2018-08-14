package study.customautowire;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
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
