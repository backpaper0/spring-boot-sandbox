package com.example;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheExample {

    @Cacheable("example")
    public String foo(final int i) {
        return "foo:" + i;
    }

    @Cacheable("example")
    public String bar(final int i) {
        return "bar:" + i;
    }
}
