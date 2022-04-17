package com.example.db2db;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.stereotype.Component;

@Component
public class Demo2UpdatingItemProcessor implements ItemProcessor<Integer, Demo2> {

	private final JdbcOperations jdbc;

	public Demo2UpdatingItemProcessor(JdbcOperations jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Demo2 process(Integer item) throws Exception {
		Demo2 model = jdbc.queryForObject(
				"select id, status from demo2 where id = ? for update",
				new BeanPropertyRowMapper<>(Demo2.class),
				item);
		model.setStatus("DONE");
		return model;
	}
}
