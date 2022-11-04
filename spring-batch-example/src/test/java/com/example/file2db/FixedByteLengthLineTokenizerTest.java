package com.example.file2db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FlatFileFormatException;

import com.example.file2db.FixedByteLengthLineTokenizer.Column;
import com.example.file2db.FixedByteLengthLineTokenizer.DataType;

/**
 * FixedByteLengthLineTokenizerのテストコード。
 *
 */
public class FixedByteLengthLineTokenizerTest {

	FixedByteLengthLineTokenizer sut;

	@BeforeEach
	void init() {
		List<Column> columns = List.of(
				new Column("foo", 6, DataType.STRING),
				new Column("bar", 3, DataType.NUMBER));
		sut = new FixedByteLengthLineTokenizer(columns, FixedByteLengthLineTokenizer.SHIFT_JIS);
	}

	@ParameterizedTest
	@CsvSource(delimiter = '|', value = {
			"hello 001 |  hello |   1",
			"hello 000 |  hello |   0", // 0
			"hello 999 |  hello | 999", // 数字の上限
			"テスト001  | テスト |   1", // 全角文字列
	})
	void test(String line, String foo, int bar) {
		FieldSet fieldSet = sut.tokenize(line);
		assertEquals(foo, fieldSet.readString("foo"));
		assertEquals(bar, fieldSet.readInt("bar"));
	}

	/**
	 * スペースのみで構成される項目は空文字列として扱う。
	 */
	@Test
	void testEmptyString() {
		FieldSet fieldSet = sut.tokenize("      001");
		assertEquals("", fieldSet.readString("foo"));
		assertEquals(1, fieldSet.readInt("bar"));
	}

	/**
	 * nullは空文字列として処理する。
	 */
	@Test
	void testNull() {
		List<Column> columns = List.of();
		FixedByteLengthLineTokenizer lineTokenizer = new FixedByteLengthLineTokenizer(columns,
				FixedByteLengthLineTokenizer.SHIFT_JIS);
		FieldSet fieldSet = lineTokenizer.tokenize(null);
		assertNotNull(fieldSet);
	}

	/**
	 * バイト数がカラム定義のバイト数合計よりも多かったり少なかったら例外をスローする。
	 */
	@ParameterizedTest
	@ValueSource(strings = {
			"12345612", // 少ない
			"1234561234", // 多い
	})
	void testInvalidByteLength(String line) {
		assertThrows(FlatFileFormatException.class, () -> sut.tokenize(line));
	}
}
