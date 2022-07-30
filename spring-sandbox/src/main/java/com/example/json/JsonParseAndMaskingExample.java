package com.example.json;

import java.io.IOException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
@ConfigurationProperties(prefix = "com.example.json")
public class JsonParseAndMaskingExample {

	@Autowired
	private ObjectMapper objectMapper;

	private Set<String> maskingTargets = Set.of();

	public String mask(String json) {
		// JSONをパースしてマスキングしつつ再構築している
		try (JsonParser parser = objectMapper.createParser(json)) {
			JsonToken token;
			StringBuilder sb = new StringBuilder();
			while (null != (token = parser.nextToken())) {
				JsonStreamContext context = parser.getParsingContext();
				switch (token) {
				case START_OBJECT:
					sb.append('{');
					break;
				case END_OBJECT:
					sb.append('}');
					break;
				case START_ARRAY:
					sb.append('[');
					break;
				case END_ARRAY:
					sb.append(']');
					break;
				case FIELD_NAME:
					if (context.inObject() && context.getCurrentIndex() > 0) {
						sb.append(',');
					}
					sb.append('"').append(parser.currentName()).append('"').append(':');
					break;
				case VALUE_STRING:
				case VALUE_NUMBER_FLOAT:
				case VALUE_NUMBER_INT:
				case VALUE_TRUE:
				case VALUE_FALSE:
				case VALUE_NULL:
					if (context.inArray() && context.getCurrentIndex() > 0) {
						sb.append(',');
					}
					boolean isString = (token == JsonToken.VALUE_STRING);
					if (isString) {
						sb.append('"');
					}
					if (context.hasCurrentName() && maskingTargets.contains(context.getCurrentName())) {
						sb.append("********");
					} else {
						String value = parser.getValueAsString();
						if (value == null || !isString) {
							sb.append(value);
						} else {
							// 文字列の場合、制御コードを除く
							value.codePoints()
									.filter(cp -> !Character.isISOControl(cp))
									.forEach(sb::appendCodePoint);
						}
					}
					if (isString) {
						sb.append('"');
					}
					break;
				case NOT_AVAILABLE:
				case VALUE_EMBEDDED_OBJECT:
				default:
					// 通常はここには到達しないはず
					throw new IllegalArgumentException(json);
				}
			}
			return sb.toString();
		} catch (@SuppressWarnings("unused") IOException e) {
			return "<parse error>";
		}
	}

	public void setMaskingTargets(Set<String> maskingTargets) {
		this.maskingTargets = maskingTargets;
	}
}
