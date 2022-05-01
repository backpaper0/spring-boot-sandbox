package com.example;

import java.util.Map;
import java.util.PrimitiveIterator;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class CharReplacementProcessor implements InitializingBean {

	private final CharReplacementProperties properties;
	private Map<Integer, Integer> codePointMap;

	public CharReplacementProcessor(CharReplacementProperties properties) {
		this.properties = properties;
	}

	public String process(String input) {
		StringBuilder sb = new StringBuilder();
		for (PrimitiveIterator.OfInt it = input.codePoints().iterator(); it.hasNext();) {
			int codePoint = it.nextInt();
			Integer replaceToMe = codePointMap.get(codePoint);
			if (replaceToMe != null) {
				sb.appendCodePoint(replaceToMe);
			} else {
				sb.appendCodePoint(codePoint);
			}
		}
		return sb.toString();
	}

	@Override
	public void afterPropertiesSet() {
		this.codePointMap = this.properties.getCharMap().entrySet().stream()
				.collect(Collectors.toMap(a -> a.getKey().codePointAt(0), a -> a.getValue().codePointAt(0)));
	}
}
