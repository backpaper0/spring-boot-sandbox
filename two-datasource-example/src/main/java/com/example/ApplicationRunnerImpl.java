package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ApplicationRunnerImpl implements ApplicationRunner {

	private final DataSource primaryDataSource;
	private final DataSource secondaryDataSource;

	public ApplicationRunnerImpl(DataSource primaryDataSource,
			@Secondary DataSource secondaryDataSource) {
		this.primaryDataSource = primaryDataSource;
		this.secondaryDataSource = secondaryDataSource;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		try (Connection con = primaryDataSource.getConnection();
				PreparedStatement pst = con.prepareStatement("select foo from primary_table");
				ResultSet rs = pst.executeQuery()) {
			System.out.println(con.getMetaData().getURL());
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
		try (Connection con = secondaryDataSource.getConnection();
				PreparedStatement pst = con.prepareStatement("select bar from secondary_table");
				ResultSet rs = pst.executeQuery()) {
			System.out.println(con.getMetaData().getURL());
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		}
	}
}
