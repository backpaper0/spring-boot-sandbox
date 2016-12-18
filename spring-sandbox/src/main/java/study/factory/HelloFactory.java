package study.factory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.annotation.ApplicationScope;

@Configuration
public class HelloFactory {

    @Bean
    @ApplicationScope
    public Hello hello() {
        return new HelloImpl();
    }
}
