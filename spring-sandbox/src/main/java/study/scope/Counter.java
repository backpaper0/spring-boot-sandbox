package study.scope;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Component
@SessionScope
public class Counter implements Serializable {

    private int value;

    public int getAndIncrement() {
        final int v = value;
        value++;
        return v;
    }
}
