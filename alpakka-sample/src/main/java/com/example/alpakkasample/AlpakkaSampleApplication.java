package com.example.alpakkasample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import akka.NotUsed;
import akka.stream.javadsl.Source;

@SpringBootApplication
@RestController
public class AlpakkaSampleApplication {

    public static void main(final String[] args) {
        SpringApplication.run(AlpakkaSampleApplication.class, args);
    }

    @RequestMapping("/")
    public Source<String, NotUsed> index() {
        return Source.repeat("Hello world!")
                .intersperse("\n")
                .take(10);
    }
}
