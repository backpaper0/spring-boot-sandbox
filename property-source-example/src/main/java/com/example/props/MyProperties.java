package com.example.props;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.convert.Delimiter;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "my-props")
@Data
public class MyProperties {

	private String prop1;
	private String prop2;
	private String prop3;
	@Delimiter("\t")
	private List<String> listProp;
}
