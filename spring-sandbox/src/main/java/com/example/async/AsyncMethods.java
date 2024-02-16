package com.example.async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class AsyncMethods {

    @Async
    public Future<String> method1() {
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }

    @Async("altExecutor")
    public Future<String> method2() {
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }
}
