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
public class Example2Service {

	private final JdbcTemplate jdbc;

	public List<Example> findAllFromLeader1() {
		return jdbc.query("select id, name from leader_names", new BeanPropertyRowMapper<>(Example.class));
	}

	@Transactional(readOnly = true)
	public List<Example> findAllFromLeader2() {
		return jdbc.query("select id, name from leader_names", new BeanPropertyRowMapper<>(Example.class));
	}

	@ReadOnlyTransaction
	public List<Example> findAllFromLeader3() {
		return jdbc.query("select id, name from leader_names", new BeanPropertyRowMapper<>(Example.class));
	}
}
