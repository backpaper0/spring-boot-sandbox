package com.example.processor;

import java.util.Objects;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.config.ExampleBatchProperties;
import com.example.entity.Example;

@Component
@StepScope
public class ExampleItemProcessor implements ItemProcessor<Integer, Example> {

	@Autowired
	private ExampleBatchProperties properties;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final String sql = "select id, content from example where id = ? for update";
	private final RowMapper<Example> rowMapper = new BeanPropertyRowMapper<>(Example.class);

	@Override
	public Example process(Integer item) throws Exception {
		if (Objects.equals(properties.getErrorId(), item)) {
			throw new RuntimeException();
		}
		Example example = jdbcTemplate.queryForObject(sql, rowMapper, item);
		example.setContent(example.getContent() + 1);
		return example;
	}
}
