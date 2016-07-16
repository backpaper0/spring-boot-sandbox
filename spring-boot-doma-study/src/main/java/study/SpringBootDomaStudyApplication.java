package study;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

@SpringBootApplication
public class SpringBootDomaStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDomaStudyApplication.class, args);
    }

    @Bean
    ObjectMapper objectMapper() {
        return Jackson2ObjectMapperBuilder.json()
                .serializerByType(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                    @Override
                    public void serialize(LocalDateTime value, JsonGenerator jgen,
                            SerializerProvider provider)
                            throws IOException, JsonProcessingException {
                        jgen.writeString(value.toString());
                    }
                }).build();
    }
}
