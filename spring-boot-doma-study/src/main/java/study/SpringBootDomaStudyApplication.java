package study;

import java.io.IOException;
import java.time.Clock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

import study.domain.Content;
import study.domain.Key;

@SpringBootApplication
public class SpringBootDomaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDomaStudyApplication.class, args);
    }

    @Autowired
    ApplicationContext context;

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    SimpleModule module() {
        SimpleModule module = new SimpleModule();
        module.addSerializer(Key.class, new JsonSerializer<Key>() {
            @Override
            public void serialize(Key value, JsonGenerator jgen,
                    SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jgen.writeNumber(value.getValue());
            }
        });
        module.addSerializer(Content.class, new JsonSerializer<Content>() {
            @Override
            public void serialize(Content value, JsonGenerator jgen,
                    SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jgen.writeString(value.getValue());
            }
        });
        return module;
    }

    @Bean
    Converter<String, Key<?>> keyConverter() {
        return new Converter<String, Key<?>>() {

            @Override
            public Key<?> convert(String source) {
                return new Key<>(Long.parseLong(source));
            }
        };
    }

    @Bean
    Converter<String, Content> contentConverter() {
        return new Converter<String, Content>() {

            @Override
            public Content convert(String source) {
                return new Content(source);
            }
        };
    }
}
