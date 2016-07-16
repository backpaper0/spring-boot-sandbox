package study.domain;

import org.seasar.doma.Domain;

@Domain(valueType = String.class)
public class Content {

    private final String value;

    public Content(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
