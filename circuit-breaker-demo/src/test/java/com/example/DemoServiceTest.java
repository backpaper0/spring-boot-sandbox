package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.common.circuitbreaker.configuration.CircuitBreakerConfigCustomizer;
import java.time.Clock;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootTest
class DemoServiceTest {

    @Autowired
    DemoService sut;

    @Autowired
    TestClock clock;

    @Autowired
    CircuitBreakerRegistry cbr;

    @Test
    void test1() {
        var cb = cbr.circuitBreaker("demo1");
        Supplier<String> s = () -> sut.demo1(false);
        Supplier<String> f = () -> sut.demo1(true);
        IntStream.range(0, 51).forEach(x -> assertEquals("[Success] 成功だよ", s.get()));
        IntStream.range(0, 49).forEach(x -> assertEquals("[Fallback] 失敗だよ", f.get()));
        assertEquals(CircuitBreaker.State.CLOSED, cb.getState());
    }

    @Test
    void test2() {
        var cb = cbr.circuitBreaker("demo2");
        Supplier<String> s = () -> sut.demo2(false);
        Supplier<String> f = () -> sut.demo2(true);
        var m = "[Fallback] CircuitBreaker 'demo2' is OPEN and does not permit further calls";

        IntStream.range(0, 50).forEach(x -> {
            assertEquals(CircuitBreaker.State.CLOSED, cb.getState());
            assertEquals("[Success] 成功だよ", s.get());
        });
        IntStream.range(0, 50).forEach(x -> {
            assertEquals(CircuitBreaker.State.CLOSED, cb.getState());
            assertEquals("[Fallback] 失敗だよ", f.get());
        });

        // 直近100回の50%失敗ならOPEN状態へ遷移する
        // (slidingWindowSize: 100, failureRateThreshold: 50)
        assertEquals(CircuitBreaker.State.OPEN, cb.getState());
        assertEquals(m, s.get());

        // 60秒間はOPEN状態で、その後HALF_OPENへ遷移する
        // (waitDurationInOpenState: 60000ms)
        clock.tick(60);
        assertEquals(m, s.get());
        assertEquals(CircuitBreaker.State.OPEN, cb.getState());

        clock.tick(1);
        assertEquals("[Success] 成功だよ", s.get());
        assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());

        // その後の10回のうち過半数が成功すればCLOSEへ遷移する
        // (permittedNumberOfCallsInHalfOpenState: 10)
        // ※ひとつ前で1回成功しているから残り9回
        IntStream.range(0, 5).forEach(x -> {
            assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());
            assertEquals("[Success] 成功だよ", s.get());
        });
        IntStream.range(0, 4).forEach(x -> {
            assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());
            assertEquals("[Fallback] 失敗だよ", f.get());
        });
        assertEquals(CircuitBreaker.State.CLOSED, cb.getState());
    }

    @Test
    void test3() {
        var cb = cbr.circuitBreaker("demo3");
        Supplier<String> s = () -> sut.demo3(false);
        Supplier<String> f = () -> sut.demo3(true);
        var m = "[Fallback] CircuitBreaker 'demo3' is OPEN and does not permit further calls";

        IntStream.range(0, 50).forEach(x -> {
            assertEquals(CircuitBreaker.State.CLOSED, cb.getState());
            assertEquals("[Success] 成功だよ", s.get());
        });
        IntStream.range(0, 50).forEach(x -> {
            assertEquals(CircuitBreaker.State.CLOSED, cb.getState());
            assertEquals("[Fallback] 失敗だよ", f.get());
        });

        // 直近100回の50%失敗ならOPEN状態へ遷移する
        // (slidingWindowSize: 100, failureRateThreshold: 50)
        assertEquals(CircuitBreaker.State.OPEN, cb.getState());
        assertEquals(m, s.get());

        // 60秒間はOPEN状態で、その後HALF_OPENへ遷移する
        // (waitDurationInOpenState: 60000ms)
        clock.tick(60);
        assertEquals(m, s.get());
        assertEquals(CircuitBreaker.State.OPEN, cb.getState());

        clock.tick(1);
        assertEquals("[Success] 成功だよ", s.get());
        assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());

        // その後の10回のうち半分以上が失敗すればOPENへ遷移する
        // (permittedNumberOfCallsInHalfOpenState: 10)
        // ※ひとつ前で1回成功しているから残り9回
        IntStream.range(0, 4).forEach(x -> {
            assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());
            assertEquals("[Success] 成功だよ", s.get());
        });
        IntStream.range(0, 5).forEach(x -> {
            assertEquals(CircuitBreaker.State.HALF_OPEN, cb.getState());
            assertEquals("[Fallback] 失敗だよ", f.get());
        });
        assertEquals(CircuitBreaker.State.OPEN, cb.getState());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        TestClock clock() {
            return TestClock.from(Clock.systemDefaultZone());
        }

        @Bean
        List<CircuitBreakerConfigCustomizer> circuitBreakerConfigCustomizers(TestClock clock) {
            return IntStream.rangeClosed(1, 3)
                    .mapToObj(i -> "demo" + i)
                    .map(id -> CircuitBreakerConfigCustomizer.of(id, builder -> builder.clock(clock)))
                    .toList();
        }
    }
}
