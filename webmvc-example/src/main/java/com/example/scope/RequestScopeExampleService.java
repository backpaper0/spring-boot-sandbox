package com.example.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestScopeExampleService {

    @Autowired
    private RequestScopeObj obj;

    public String getId() {
        return obj.getId();
    }
}
