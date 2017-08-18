package study.entitybody;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HttpMessageConverterSample {
    public static void main(final String[] args) {
        SpringApplication.run(HttpMessageConverterSample.class, args);
    }

    @PostMapping
    String post(@RequestBody final Hoge hoge) {
        return "OK";
    }
}

enum Hoge {
    SINGLETON
}

@Component
class HogeHttpMessageConverter implements HttpMessageConverter<Hoge> {

    @Override
    public boolean canRead(final Class<?> clazz, final MediaType mediaType) {
        return clazz == Hoge.class;
    }

    @Override
    public boolean canWrite(final Class<?> clazz, final MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return Collections.singletonList(MediaType.ALL);
    }

    @Override
    public Hoge read(final Class<? extends Hoge> clazz, final HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return Hoge.SINGLETON;
    }

    @Override
    public void write(final Hoge t, final MediaType contentType,
            final HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        throw new UnsupportedOperationException();
    }
}