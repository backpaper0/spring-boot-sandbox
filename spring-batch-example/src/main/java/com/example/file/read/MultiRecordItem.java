package com.example.file.read;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public interface MultiRecordItem {

	@lombok.Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Header implements MultiRecordItem {
		private String classifier;
		private String filler;
	}

	@lombok.Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Data implements MultiRecordItem {
		private String classifier;
		private String name;
		private int number;
	}

	@lombok.Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Footer implements MultiRecordItem {
		private String classifier;
		private int count;
		private String filler;
	}
}
