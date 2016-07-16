package study.controller.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import study.domain.Content;
import study.domain.Key;

// http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html#mvc-config-conversion
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new Converter<String, Key<?>>() {

            @Override
            public Key<?> convert(String source) {
                return new Key<>(Long.parseLong(source));
            }
        });
        registry.addConverter(new Converter<String, Content>() {

            @Override
            public Content convert(String source) {
                return new Content(source);
            }
        });
    }
}
