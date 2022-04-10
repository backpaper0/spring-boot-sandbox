package com.example.service;

import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.core.annotation.ReadOnly;
import com.example.model.Example;

@Service
@Transactional
public class ExampleService {

	private final JdbcTemplate jdbc;

	public ExampleService(JdbcTemplate jdbc) {
		this.jdbc = jdbc;
	}

	public List<Example> findAll() {
		return jdbc.query("select id, name from primary_names", new BeanPropertyRowMapper<>(Example.class));
	}

	@Transactional(readOnly = true)
	public List<Example> findAllFromReadReplica() {
		return jdbc.query("select id, name from read_replica_names", new BeanPropertyRowMapper<>(Example.class));
	}

	@ReadOnly
	public List<Example> findAllFromReadReplica2() {
		return jdbc.query("select id, name from read_replica_names", new BeanPropertyRowMapper<>(Example.class));
	}
}
