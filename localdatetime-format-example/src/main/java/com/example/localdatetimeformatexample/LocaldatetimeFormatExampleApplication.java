package com.example.localdatetimeformatexample;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class LocaldatetimeFormatExampleApplication {

    public static void main(final String[] args) throws Exception {
        final ConfigurableApplicationContext context = SpringApplication
                .run(LocaldatetimeFormatExampleApplication.class, args);

        final URL url = new URL("http://localhost:8080");
        final HttpURLConnection con = (HttpURLConnection) url.openConnection();
        final InputStream in = (InputStream) con.getContent();
        final byte[] b = in.readAllBytes();
        final String s = new String(b);
        System.out.println(s);

        SpringApplication.exit(context);
    }

    @GetMapping("/")
    Example get() {
        return new Example(LocalDateTime.now());
    }

    public static class Example {

        private final LocalDateTime value;

        public Example(final LocalDateTime value) {
            this.value = value;
        }

        public LocalDateTime getValue() {
            return value;
        }
    }
}
