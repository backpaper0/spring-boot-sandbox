package com.example.demo.event;

import java.util.Objects;
import org.springframework.context.ApplicationEvent;

public class DomaEvent extends ApplicationEvent {

    private final Object context;

    public DomaEvent(final Object source, final Object context) {
        super(source);
        this.context = Objects.requireNonNull(context);
    }

    public Object getContext() {
        return context;
    }
}
