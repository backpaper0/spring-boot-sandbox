package com.example.service;

import java.util.List;

import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.annotation.ReadOnlyTransaction;
import com.example.model.Example;

@Service
@Transactional
public class Example2Service {

	private final DataClassRowMapper<Example> rowMapper = DataClassRowMapper.newInstance(Example.class);
	private final JdbcClient jdbc;

	public Example2Service(JdbcClient jdbc) {
		this.jdbc = jdbc;
	}

	public List<Example> findAllFromLeader1() {
		return jdbc.sql("select id, name from leader_names").query(rowMapper).list();
	}

	@Transactional(readOnly = true)
	public List<Example> findAllFromLeader2() {
		return jdbc.sql("select id, name from leader_names").query(rowMapper).list();
	}

	@ReadOnlyTransaction
	public List<Example> findAllFromLeader3() {
		return jdbc.sql("select id, name from leader_names").query(rowMapper).list();
	}
}
