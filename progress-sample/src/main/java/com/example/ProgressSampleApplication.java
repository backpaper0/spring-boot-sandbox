package com.example;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.TimeUnit;
import java.util.function.DoubleConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@EnableAsync
@SpringBootApplication
public class ProgressSampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgressSampleApplication.class, args);
    }
}

@Controller
class SampleController {

    @Autowired
    SampleService service;

    @GetMapping("/")
    String index() {
        return "index";
    }

    @GetMapping("/run")
    SseEmitter run() throws Exception {
        SseEmitter emitter = new SseEmitter();
        service.post(a -> {
            try {
                if (a < 1d) {
                    emitter.send(a);
                } else {
                    emitter.send(1.0d);
                    emitter.complete();
                }
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        });
        return emitter;
    }
}

@Service
class SampleService {

    @Async
    public void post(DoubleConsumer consumer) {
        for (int i = 0; i < 100; i++) {
            consumer.accept(i / 100d);
            try {
                TimeUnit.MILLISECONDS.sleep(50);
            } catch (InterruptedException e) {
            }
        }
        consumer.accept(1d);
    }
}