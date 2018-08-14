package study.factorybean;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = FactoryBeanExample.class, webEnvironment = WebEnvironment.NONE)
public class FactoryBeanExampleTest {

    @Autowired
    private FooService service;

    @Test
    public void run() {
        service.run();
    }
}
