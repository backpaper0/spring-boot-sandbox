package study.requestparam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RequestParamSample {

    public static void main(final String[] args) {
        SpringApplication.run(RequestParamSample.class, args);
    }

    //curl "localhost:8080?a=aaa&b=bbb&c=ccc&d=ddd"

    @GetMapping("/")
    String get(
            @RequestParam final ValueOf a,
            @RequestParam final Of b,
            @RequestParam final From c,
            @RequestParam final Constructor d) {
        return String.format("%s, %s, %s, %s", a, b, c, d);
    }
}

interface ValueOf {
    static ValueOf valueOf(final String s) {
        return new ValueOf() {
            @Override
            public String toString() {
                return s;
            }
        };
    }
}

interface Of {
    static Of of(final String s) {
        return new Of() {
            @Override
            public String toString() {
                return s;
            }
        };
    }
}

interface From {
    static From from(final String s) {
        return new From() {
            @Override
            public String toString() {
                return s;
            }
        };
    }
}

class Constructor {
    private final String s;
    public Constructor(final String s) {
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }
}