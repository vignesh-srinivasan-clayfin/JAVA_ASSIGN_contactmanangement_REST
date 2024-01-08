package com.resource.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool.PoolInitializationException;

public class DatabaseConnection {

	private String dbUrl = "jdbc:h2:tcp://localhost/~/Userdisk/Java_Files/Tools/h2_storage";
	private String userName = "";
	private String password = "";

	HikariConfig config = new HikariConfig();

	public DatabaseConnection() {

	}

	public HikariDataSource getDB() throws ClassNotFoundException,PoolInitializationException {

		HikariDataSource dataSource = null;

		Class.forName("org.h2.Driver");
		config.setJdbcUrl(dbUrl);
		config.setUsername(userName);
		config.setPassword(password);
		config.setMaximumPoolSize(10); // Max pool size
		config.setMinimumIdle(5); // Minimum idle connections
		config.setConnectionTimeout(5000); // Connection timeout in milliseconds
		dataSource = new HikariDataSource(config);

		return dataSource;

	}

	public Connection getDBConnection() {

		try {

			return getDB().getConnection();

		} catch (ClassNotFoundException | SQLException | PoolInitializationException e) {
			
			e.printStackTrace();

			return null;

		}

	}

}
