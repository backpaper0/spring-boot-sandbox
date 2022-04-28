package com.example.file2db;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class FixedByteLengthLineTokenizer implements LineTokenizer {

	private final List<Column> columns;
	private final List<String> names;

	public FixedByteLengthLineTokenizer(List<Column> columns) {
		this.columns = columns;
		this.names = columns.stream().map(a -> a.name).toList();
	}

	@Override
	public FieldSet tokenize(String line) {
		try {
			List<String> tokens = new ArrayList<>();
			byte[] bytes;
			bytes = line.getBytes("Windows-31J");
			int index = 0;
			for (Column column : columns) {
				String token = new String(bytes, index, column.byteLength, "Windows-31J");
				tokens.add(column.dataType.apply(token));
				index += column.byteLength;
			}
			return new DefaultFieldSet(tokens.toArray(String[]::new), names.toArray(String[]::new));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public static class Column {

		private final String name;
		private final int byteLength;
		private final DataType dataType;

		public Column(String name, int byteLength, DataType dataType) {
			this.name = name;
			this.byteLength = byteLength;
			this.dataType = dataType;
		}
	}

	public enum DataType {
		STRING,
		NUMBER;

		String apply(String value) {
			switch (this) {
			case NUMBER: {
				int index = 0;
				while (index < value.length()) {
					if (value.charAt(index) != '0') {
						break;
					}
					index++;
				}
				return value.substring(index);
			}
			case STRING: {
				int index = value.length() - 1;
				while (index > -1) {
					if (!Character.isSpaceChar(value.charAt(index))) {
						break;
					}
					index--;
				}
				return value.substring(0, index + 1);
			}
			default:
				return value;
			}
		}
	}
}
