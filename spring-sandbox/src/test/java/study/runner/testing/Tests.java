package study.runner.testing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(
        webEnvironment = WebEnvironment.NONE,
        properties = "spring.profiles.active=test")
@Retention(RetentionPolicy.RUNTIME)
public @interface Tests {
}
