package study.requestscope;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RequestScopeExample.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class RequestScopeExampleTest {

    @Test
    public void test() throws Exception {
    }
}
