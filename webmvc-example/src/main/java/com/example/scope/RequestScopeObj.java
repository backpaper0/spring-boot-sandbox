package com.example.scope;

import java.util.UUID;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class RequestScopeObj {

    private final String id = UUID.randomUUID().toString();

    public String getId() {
        return id;
    }
}
