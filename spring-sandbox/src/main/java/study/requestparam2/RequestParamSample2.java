package study.requestparam2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RequestParamSample2 {

    /*
     * @RequestParamが無くてもバリューオブジェクトで受け取れるっぽいので検証。
     * 
     * 選択されるHandlerMethodArgumentResolverの実装が異なり、
     * たまたまModelAttributeMethodProcessorで上手く受け取れていた様子。
     *
     */

    /**
     * 
     * @param foo
     * @return
     * @see org.springframework.web.method.annotation.ModelAttributeMethodProcessor
     */
    @GetMapping("1")
    String get1(final Foo foo) {
        /*
         * ModelAttributeMethodProcessorはrequest.getAttribute()もしくは
         * request.getParameter()で取得した値を利用する。
         */
        return foo.toString();
    }

    /**
     * 
     * @param foo
     * @return
     * @see org.springframework.web.method.annotation.RequestParamMethodArgumentResolver
     */
    @GetMapping("2")
    String get2(@RequestParam final Foo foo) {
        return foo.toString();
    }

    public static void main(final String[] args) {
        SpringApplication.run(RequestParamSample2.class, args);
    }
}

class Foo {

    private final String value;

    public Foo(final String value) {
        this.value = value;
        new Throwable().printStackTrace(System.out);
    }

    @Override
    public String toString() {
        return value;
    }
}
