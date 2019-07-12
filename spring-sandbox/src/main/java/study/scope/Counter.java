package study.scope;

import java.io.Serializable;

import org.springframework.web.context.annotation.SessionScope;

@SessionScope
public class Counter implements Serializable {

    private int value;

    public int getAndIncrement() {
        int v = value;
        value++;
        return v;
    }
}
