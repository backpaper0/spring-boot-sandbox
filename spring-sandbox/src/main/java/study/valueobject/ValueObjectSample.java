package study.valueobject;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

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

    public static void main(final String[] args) {
        SpringApplication.run(ValueObjectSample.class, args);
    }

    @RequestMapping("/")
    ReturnValue get(@RequestParam final ValueObject vo) {
        return new ReturnValue(vo);
    }

    @Bean
    ObjectMapper jackson2ObjectMapperBuilder(final Jackson2ObjectMapperBuilder builder) {
        return builder.serializerByType(ValueObject.class,
                SerializerWrapper.create(ValueObject::getValue)).build();
    }
}

class SerializerWrapper<T> extends JsonSerializer<T> {

    final Function<T, String> f;

    public SerializerWrapper(final Function<T, String> f) {
        this.f = f;
    }

    @Override
    public void serialize(final T value, final JsonGenerator gen,
            final SerializerProvider serializers)
            throws IOException, JsonProcessingException {
        gen.writeString(f.apply(value));
    }

    static <T> SerializerWrapper<T> create(final Function<T, String> f) {
        return new SerializerWrapper<>(f);
    }
}

class ValueObject {

    private final String value;

    public ValueObject(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public String getValue() {
        return value;
    }
}

class ReturnValue {

    public final ValueObject vo;

    public ReturnValue(final ValueObject vo) {
        this.vo = vo;
    }
}
