package com.example.scope;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/request-scope-example")
public class RequestScopeExampleController {

    @Autowired
    private RequestScopeExampleService service;

    @Autowired
    private RequestScopeObj obj;

    @GetMapping
    public Object getId() {
        return Map.of("direct", obj.getId(), "via-service", service.getId());
    }
}
