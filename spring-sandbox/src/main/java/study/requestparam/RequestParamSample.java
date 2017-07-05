package study.requestparam;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.ConditionalGenericConverter;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class RequestParamSample {

    public static void main(final String[] args) {
        SpringApplication.run(RequestParamSample.class, args);
    }

    //curl "localhost:8080?a=aaa&b=bbb&c=ccc&d=ddd&e=eee&f=fff"

    // see: ObjectToObjectConverter#getValidatedMember

    @GetMapping("/")
    String get(
            @RequestParam final ValueOf a,
            @RequestParam final Of b,
            @RequestParam final From c,
            @RequestParam final Constructor d,
            @RequestParam final EnumValueOf e,
            @RequestParam final EnumOf f) {
        return String.format("%s, %s, %s, %s, %s, %s", a, b, c, d, e, f);
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

enum EnumValueOf {
    eee, xxx, yyy;
    public static EnumValueOf of(final String s) {
        return xxx;
    }
    public static EnumValueOf from(final String s) {
        return yyy;
    }
}

@Retention(RetentionPolicy.RUNTIME)
@interface EnumOfConverted {
}

@EnumOfConverted
enum EnumOf {
    SINGLETON("fff");
    private final String s;
    EnumOf(final String s) {
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }
    public static EnumOf of(final String s) {
        return Arrays.stream(values())
                .filter(a -> Objects.equals(s, a.s))
                .findAny()
                .orElse(null);
    }
}

@Component
class EnumOfConverter implements ConditionalGenericConverter, InitializingBean {

    private final GenericConversionService service;

    public EnumOfConverter(final GenericConversionService service) {
        this.service = Objects.requireNonNull(service);
    }

    @Override
    public Set<ConvertiblePair> getConvertibleTypes() {
        return Collections.singleton(new ConvertiblePair(String.class, Enum.class));
    }

    @Override
    public Object convert(final Object source, final TypeDescriptor sourceType,
            final TypeDescriptor targetType) {
        final Method method = ReflectionUtils.findMethod(targetType.getType(), "of", String.class);
        ReflectionUtils.makeAccessible(method);
        try {
            return method.invoke(null, source);
        } catch (final ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean matches(final TypeDescriptor sourceType, final TypeDescriptor targetType) {
        return targetType.getType().getAnnotation(EnumOfConverted.class) != null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        service.addConverter(this);
    }
}
