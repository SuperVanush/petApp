package com.example.demo.dao;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {

    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
            ds.setUsername("postgres");
            ds.setPassword("5577166");
            dataSource = ds;
        }
        return dataSource;
    }

    public static Connection getConnetion() throws SQLException {
        return getDataSource().getConnection();
    }

    private DaoFactory() {
    }
}
