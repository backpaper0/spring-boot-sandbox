package study.runner.testing;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(
        webEnvironment = WebEnvironment.NONE,
        properties = "spring.profiles.active=test")
@RunWith(SpringRunner.class)
public class CommandLineRunnerTestingSampleTest {

    @Test
    public void test() {
        //Profileを利用してCommandLineRunnerをコンポーネント登録しない
    }
}
