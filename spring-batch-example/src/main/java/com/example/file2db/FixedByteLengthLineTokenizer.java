package com.example.file2db;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FlatFileFormatException;
import org.springframework.batch.item.file.transform.LineTokenizer;

public class FixedByteLengthLineTokenizer implements LineTokenizer {

	public static final Charset SHIFT_JIS = Charset.forName("Shift_JIS");
	public static final Charset MS932 = Charset.forName("MS932");

	private final List<Column> columns;
	private final List<String> names;
	private final int totalByteLength;
	private final Charset charset;

	public FixedByteLengthLineTokenizer(List<Column> columns, Charset charset) {
		this.columns = columns;
		this.names = columns.stream().map(a -> a.name).toList();
		this.totalByteLength = columns.stream().mapToInt(a -> a.byteLength).sum();
		this.charset = charset;
	}

	@Override
	public FieldSet tokenize(String line) {
		List<String> tokens = new ArrayList<>();
		byte[] bytes;
		if (line != null) {
			bytes = line.getBytes(charset);
		} else {
			bytes = new byte[0];
		}
		if (bytes.length != totalByteLength) {
			throw new FlatFileFormatException("バイト数が異なります", line);
		}
		int index = 0;
		for (Column column : columns) {
			String token = new String(bytes, index, column.byteLength, charset);
			tokens.add(column.dataType.apply(token));
			index += column.byteLength;
		}
		return new DefaultFieldSet(tokens.toArray(String[]::new), names.toArray(String[]::new));
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
				while (index < value.length() - 1) {
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
