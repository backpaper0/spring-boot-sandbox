package com.example.props;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.Delimiter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-props")
public class MyProperties {

    private String prop1;
    private String prop2;
    private String prop3;
    @Delimiter("\t")
    private List<String> listProp;

    public String getProp1() {
        return prop1;
    }

    public void setProp1(String prop1) {
        this.prop1 = prop1;
    }

    public String getProp2() {
        return prop2;
    }

    public void setProp2(String prop2) {
        this.prop2 = prop2;
    }

    public String getProp3() {
        return prop3;
    }

    public void setProp3(String prop3) {
        this.prop3 = prop3;
    }

    public List<String> getListProp() {
        return listProp;
    }

    public void setListProp(List<String> listProp) {
        this.listProp = listProp;
    }
}
