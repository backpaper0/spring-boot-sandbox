package com.example.excludefilter;

import java.io.IOException;
import java.util.Objects;

import org.springframework.boot.context.TypeExcludeFilter;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

public class ExcludeComExampleTestFilter extends TypeExcludeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		return metadataReader.getClassMetadata().getClassName().startsWith("com.example.test.");
	}

	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getClass());
	}
}
