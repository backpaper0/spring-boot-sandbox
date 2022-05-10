package com.example.db2file;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

public class FullWidthUtil {

	private static final String CHARSET_NAME = "Windows-31J";

	public static String fillPad(String value, int byteLength) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			out.write(value.getBytes(CHARSET_NAME));
			byte[] pad = "　".getBytes(CHARSET_NAME);
			while (out.size() < byteLength) {
				out.write(pad);
			}
			return out.toString(CHARSET_NAME);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}
