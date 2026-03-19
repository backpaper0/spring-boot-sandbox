package study.proxy;

import com.example.misc.Foo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

@SpringBootApplication
public class App {

    @Bean
    public Foo foo1() {
        return new Foo("a");
    }

    @Bean
    @Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Foo foo2() {
        return new Foo("b");
    }
}
