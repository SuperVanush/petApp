package com.example.demo.dao;

import com.example.demo.Ecxeption.MyException;
import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DaoFactory {

    private static final String DB_URL = System.getProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/postgres");
    private static final String DB_USERNAME = System.getProperty("jdbcUserName", "postgres");
    private static final String DB_PASS = System.getProperty("jdbcPassword", "5577166");


    private static DataSource dataSource;

    public static DataSource getDataSource() {
        if (dataSource == null) {
            HikariDataSource ds = new HikariDataSource();
            ds.setJdbcUrl(DB_URL);
            ds.setUsername(DB_USERNAME);
            ds.setPassword(DB_PASS);
            dataSource = ds;
            initDataBase();
        }
        return dataSource;
    }

    private static void initDataBase() {
        try {
            DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
            Liquibase liquibase = new Liquibase(
                    "liquibase.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update(new Contexts());
        } catch (SQLException | LiquibaseException e) {
            throw new MyException(e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    private DaoFactory() {
    }
}
