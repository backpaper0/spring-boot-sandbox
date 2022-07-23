package com.example.service;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.annotation.ReadOnlyTransaction;
import com.example.model.Example;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Example1Service {

	private final JdbcTemplate jdbc;
	private final Example2Service example2Service;

	public List<Example> findAllFromLeader1() {
		return jdbc.query("select id, name from leader_names", new BeanPropertyRowMapper<>(Example.class));
	}

	@Transactional(readOnly = true)
	public List<Example> findAllFromFollower1() {
		return jdbc.query("select id, name from follower_names", new BeanPropertyRowMapper<>(Example.class));
	}

	@ReadOnlyTransaction
	public List<Example> findAllFromFollower2() {
		return jdbc.query("select id, name from follower_names", new BeanPropertyRowMapper<>(Example.class));
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
