package com.example.processor;

import java.time.LocalDateTime;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.example.entity.Example;

import lombok.Setter;

@Component
@StepScope
@ConfigurationProperties(prefix = "example")
public class ExampleItemProcessor implements ItemProcessor<Integer, Example> {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	private final String sql = "select id, content from example where id = ? for update";
	private final RowMapper<Example> rowMapper = new BeanPropertyRowMapper<>(Example.class);
	private final String content = LocalDateTime.now().toString();
	@Setter
	private Integer errorId;

	@Override
	public Example process(Integer item) throws Exception {
		if (errorId != null && errorId.equals(item)) {
			throw new RuntimeException();
		}
		Example example = jdbcTemplate.queryForObject(sql, rowMapper, item);
		example.setContent(content);
		return example;
	}
}
