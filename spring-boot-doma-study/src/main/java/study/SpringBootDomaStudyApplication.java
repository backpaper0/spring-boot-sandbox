package study;

import java.io.IOException;
import java.time.Clock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import study.domain.Content;
import study.domain.Key;

@SpringBootApplication
public class SpringBootDomaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDomaStudyApplication.class, args);
    }

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder.serializerByType(Key.class, new JsonSerializer<Key<?>>() {
            @Override
            public void serialize(Key<?> value, JsonGenerator jgen,
                    SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jgen.writeNumber(value.getValue());
            }
        }).serializerByType(Content.class, new JsonSerializer<Content>() {
            @Override
            public void serialize(Content value, JsonGenerator jgen,
                    SerializerProvider provider)
                    throws IOException, JsonProcessingException {
                jgen.writeString(value.getValue());
            }
        }).build();
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
}
