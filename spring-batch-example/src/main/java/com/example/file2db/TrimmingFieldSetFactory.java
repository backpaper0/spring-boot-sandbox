package com.example.file2db;

import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.item.file.transform.FieldSetFactory;

public class TrimmingFieldSetFactory implements FieldSetFactory {

	private final FieldSetFactory fieldSetFactory;
	private final TrimmingType[] trimmingTypes;

	public TrimmingFieldSetFactory(FieldSetFactory fieldSetFactory, TrimmingType... trimmingTypes) {
		this.fieldSetFactory = fieldSetFactory;
		this.trimmingTypes = trimmingTypes;
	}

	@Override
	public FieldSet create(String[] values, String[] names) {
		return fieldSetFactory.create(trimming(values), names);
	}

	@Override
	public FieldSet create(String[] values) {
		return fieldSetFactory.create(trimming(values));
	}

	private String[] trimming(String[] values) {
		if (values.length != trimmingTypes.length) {
			//TODO throw exception
		}
		String[] newValues = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			newValues[i] = trimmingTypes[i].trim(values[i]);
		}
		return newValues;
	}

	public enum TrimmingType {
		PREFIX_ZERO,
		SUFFIX_SPACE,
		NONE;

		String trim(String value) {
			switch (this) {
			case PREFIX_ZERO: {
				int index = 0;
				while (index < value.length()) {
					if (value.charAt(index) != '0') {
						break;
					}
					index++;
				}
				return value.substring(index);
			}
			case SUFFIX_SPACE:
				int index = value.length() - 1;
				while (index > -1) {
					if (!Character.isSpaceChar(value.charAt(index))) {
						break;
					}
					index--;
				}
				return value.substring(0, index + 1);
			default:
				return value;
			}
		}
	}
}
