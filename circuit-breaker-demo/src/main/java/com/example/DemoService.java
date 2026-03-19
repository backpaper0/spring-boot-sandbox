package com.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DemoService {

    private static final Logger logger = LoggerFactory.getLogger(DemoService.class);

    private final RestClient rest;
    private final DemoProperties props;
    private final Resilience4JCircuitBreakerFactory cbFactory;

    public DemoService(RestClient rest, Resilience4JCircuitBreakerFactory circuitBreakerFactory, DemoProperties props) {
        this.rest = rest;
        this.cbFactory = circuitBreakerFactory;
        this.props = props;
    }

    public String get(int status) {
        return cbFactory.create("demo").run(() -> getInternal(status), this::defaultGet);
    }

    String getInternal(int status) {
        logger.info("Start getInternal()");
        try {
            var uri = UriComponentsBuilder.fromUri(props.getEndpoint())
                    .pathSegment("status", String.valueOf(status))
                    .build()
                    .toUri();
            return rest.get()
                    .uri(uri)
                    .retrieve()
                    .toBodilessEntity()
                    .getStatusCode()
                    .toString();
        } catch (Throwable t) {
            logger.info("Exception occurred");
            throw t;
        }
    }

    String defaultGet(Throwable t) {
        logger.info("Fallback to defaultGet()");
        return "!Fallback! " + t.getMessage();
    }
}
