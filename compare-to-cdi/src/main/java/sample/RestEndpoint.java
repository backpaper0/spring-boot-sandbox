package sample;

import java.util.StringJoiner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sample.componentdef.Foo;
import sample.factorymethod.Bar;
import sample.scope.PrototypeBaz;
import sample.scope.SingletonBaz;

@RestController
public class RestEndpoint {

    @Autowired
    Foo foo;
    @Autowired
    Bar bar;
    @Autowired
    SingletonBaz singletonBaz;
    @Autowired
    PrototypeBaz prototypeBaz;

    @RequestMapping("/foo")
    String foo() {
        return foo.get();
    }

    @RequestMapping("/bar")
    String bar() {
        return bar.get();
    }

    @RequestMapping("/baz")
    String baz() {
        StringJoiner s = new StringJoiner(System.lineSeparator());
        s.add(String.format("%s ( %s )", singletonBaz.get(), singletonBaz.getClass()));
        s.add(String.format("%s ( %s )", prototypeBaz.get(), prototypeBaz.getClass()));
        return s.toString();
    }
}
