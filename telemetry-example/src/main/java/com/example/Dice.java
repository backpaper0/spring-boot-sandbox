package com.example;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import io.opentelemetry.instrumentation.annotations.SpanAttribute;
import io.opentelemetry.instrumentation.annotations.WithSpan;

@Component
public class Dice {

    @WithSpan
    public List<Integer> rollTheDice(@SpanAttribute int rolls) {
        return IntStream.range(0, rolls).map(a -> rollOnce()).boxed().toList();
    }

    private int rollOnce() {
        return ThreadLocalRandom.current().nextInt(1, 7);
    }
}
