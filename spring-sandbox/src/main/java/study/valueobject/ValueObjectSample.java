package study.valueobject;

import java.io.IOException;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

@SpringBootApplication
@RestController
public class ValueObjectSample {

    public static void main(String[] args) {
        SpringApplication.run(ValueObjectSample.class, args);
    }

    @RequestMapping("/")
    ReturnValue get(@RequestParam ValueObject vo) {
        return new ReturnValue(vo);
    }

    @Bean
    ObjectMapper jackson2ObjectMapperBuilder(Jackson2ObjectMapperBuilder builder) {
        return builder.serializerByType(ValueObject.class, new JsonSerializer<ValueObject>() {
            @Override
            public void serialize(ValueObject value, JsonGenerator gen,
                    SerializerProvider serializers) throws IOException, JsonProcessingException {
                gen.writeString(value.getValue());
            }
        }).build();
    }
}

class ValueObject {

    private final String value;

    public ValueObject(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public String getValue() {
        return value;
    }
}

class ReturnValue {

    public final ValueObject vo;

    public ReturnValue(ValueObject vo) {
        this.vo = vo;
    }
}
