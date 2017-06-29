package com.example.demo.even2;

import java.util.Objects;
import org.springframework.context.ApplicationEvent;

public class DomaApplicationEvent extends ApplicationEvent {

    private final Object context;

    public DomaApplicationEvent(final Object source, final Object context) {
        super(source);
        this.context = Objects.requireNonNull(context);
    }

    public Object getContext() {
        return context;
    }
}
