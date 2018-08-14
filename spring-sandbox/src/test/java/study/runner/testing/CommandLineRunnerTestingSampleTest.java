package study.runner.testing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(
        webEnvironment = WebEnvironment.NONE,
        properties = "spring.profiles.active=test")
@ExtendWith(SpringExtension.class)
public class CommandLineRunnerTestingSampleTest {

    @Test
    public void test() {
        //Profileを利用してCommandLineRunnerをコンポーネント登録しない
    }
}
