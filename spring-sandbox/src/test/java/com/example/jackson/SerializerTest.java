package com.example.jackson;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

class SerializerTest {

	@Test
	void serialize() throws Exception {
		final var c1 = new Child(1, "aaa");
		final var c2 = new Child(2, "bbb");
		final var c3 = new Child(3, "ccc");
		final var p1 = new Parent(4, "ddd", List.of(c1, c2, c3));
		final var root = new Root("eee", p1);

		final ObjectMapper mapper = new ObjectMapper();
		final String json = mapper.writeValueAsString(root);

		final JsonNode node = mapper.readTree(json);

		final JsonNode expected = mapper.readTree(
				"{'description':'eee','parent':{'id':4,'name':'ddd','1':'aaa','2':'bbb','3':'ccc'}}"
						.replace('\'', '"'));

		assertEquals(expected, node);

	}

	static class Root {

		private final String description;
		private final Parent parent;

		public Root(final String description, final Parent parent) {
			this.description = description;
			this.parent = parent;
		}

		public String getDescription() {
			return description;
		}

		public Parent getParent() {
			return parent;
		}
	}

	@JsonSerialize(using = ParentSerializer.class)
	static class Parent {

		private final int id;
		private final String name;
		private final List<Child> children;

		public Parent(final int id, final String name, final List<Child> children) {
			this.id = id;
			this.name = name;
			this.children = children;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public List<Child> getChildren() {
			return children;
		}
	}

	static class Child {

		private final int id;
		private final String name;

		public Child(final int id, final String name) {
			this.id = id;
			this.name = name;
		}

		public int getId() {
			return id;
		}

		public String getName() {
			return name;
		}
	}

	static class ParentSerializer extends JsonSerializer<Parent> {

		@Override
		public void serialize(final Parent value, final JsonGenerator gen,
				final SerializerProvider serializers)
				throws IOException {
			gen.writeStartObject();
			gen.writeNumberField("id", value.getId());
			gen.writeStringField("name", value.getName());
			for (final Child child : value.getChildren()) {
				gen.writeStringField(String.valueOf(child.getId()), child.getName());
			}
			gen.writeEndObject();
		}
	}
}
