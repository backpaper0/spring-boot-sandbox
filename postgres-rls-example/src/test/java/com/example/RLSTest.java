package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RLSTest {

	@Autowired
	private AnimalRepository repos;

	@ParameterizedTest
	@CsvSource(value = {
			"neko   | 1ごう, みかっち, ラムネ",
			"koguma | パッチ, のりお, マーティー",
			"usagi  | ミッチェル, フランソワ, クリスチーヌ",
	}, delimiter = '|')
	void getTenantIdByTenant1(String species, String expectedCommaSeparatedNames) {
		SpeciesHolder.set(species);
		List<String> names = repos.findNames();
		List<String> expectedNames = List.of(expectedCommaSeparatedNames.split("\\s*,\\s*"));
		assertEquals(expectedNames, names);
	}
}
