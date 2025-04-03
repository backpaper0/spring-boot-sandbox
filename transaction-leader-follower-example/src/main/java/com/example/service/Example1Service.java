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
public class Example1Service {

	private final DataClassRowMapper<Example> rowMapper = DataClassRowMapper.newInstance(Example.class);
	private final JdbcClient jdbc;
	private final Example2Service example2Service;

	public Example1Service(JdbcClient jdbc, Example2Service example2Service) {
		this.jdbc = jdbc;
		this.example2Service = example2Service;
	}

	public List<Example> findAllFromLeader1() {
		return jdbc.sql("select id, name from leader_names").query(rowMapper).list();
	}

	@Transactional(readOnly = true)
	public List<Example> findAllFromFollower1() {
		return jdbc.sql("select id, name from follower_names").query(rowMapper).list();
	}

	@ReadOnlyTransaction
	public List<Example> findAllFromFollower2() {
		return jdbc.sql("select id, name from follower_names").query(rowMapper).list();
	}

	public List<Example> nestFindAllFromLeader1() {
		return example2Service.findAllFromLeader1();
	}

	public List<Example> nestFindAllFromLeader2() {
		return example2Service.findAllFromLeader2();
	}

	public List<Example> nestFindAllFromLeader3() {
		return example2Service.findAllFromLeader3();
	}
}
