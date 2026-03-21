package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {

    private final Logger log = LoggerFactory.getLogger(ExampleService.class);

    @Retryable(
            // 初回実行+3回までリトライ
            maxRetries = 3,
            delay = 0)
    public String demo(ExampleContext context) {
        log.info("{} - start", context.status());
        context.countUp();
        if (context.isBelowMax()) {
            log.info("{} - throw exception", context.status());
            throw new ExampleException();
        }
        log.info("{} - exit", context.status());
        return context.status();
    }

    public static class ExampleContext {
        private String name;
        private int max;
        private int count;

        public ExampleContext(String name, int max) {
            this.name = name;
            this.max = max;
            this.count = 0;
        }

        public void countUp() {
            this.count++;
        }

        public boolean isBelowMax() {
            return count < max;
        }

        public String status() {
            return "%s(%d/%d)".formatted(name, count, max);
        }
    }

    public static class ExampleException extends RuntimeException {}
}
