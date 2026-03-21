package com.example;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    private final Resilience4JCircuitBreakerFactory cbFactory;

    public DemoService(Resilience4JCircuitBreakerFactory circuitBreakerFactory) {
        this.cbFactory = circuitBreakerFactory;
    }

    // アノテーションで適用できる
    @CircuitBreaker(name = "demo1", fallbackMethod = "fallback")
    public String demo1(boolean error) {
        return getInternal(error);
    }

    // Resilience4JCircuitBreakerFactoryを直接操作してもよい
    public String demo2(boolean error) {
        return cbFactory.create("demo2").run(() -> getInternal(error), this::fallback);
    }

    @CircuitBreaker(name = "demo3", fallbackMethod = "fallback")
    public String demo3(boolean error) {
        return getInternal(error);
    }

    private String getInternal(boolean error) {
        if (error) {
            throw new RuntimeException("失敗だよ");
        }
        return "[Success] " + "成功だよ";
    }

    private String fallback(Throwable t) {
        return "[Fallback] " + t.getMessage();
    }
}
