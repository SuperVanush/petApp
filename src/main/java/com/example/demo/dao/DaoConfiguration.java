package com.example.demo.dao;

import com.zaxxer.hikari.HikariDataSource;
import liquibase.Contexts;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@ComponentScan
@Configuration
public class DaoConfiguration {

    @Bean
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl(System.getProperty("jdbcUrl", "jdbc:postgresql://localhost:5432/postgres"));
        ds.setUsername(System.getProperty("jdbcUserName", "postgres"));
        ds.setPassword(System.getProperty("jdbcPassword", "5577166"));
        return ds;
    }

    @Bean
    public Liquibase liquibase(DataSource dataSource) throws Exception {
        DatabaseConnection connection = new JdbcConnection(dataSource.getConnection());
        Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(connection);
        Liquibase liquibase = new Liquibase(
                "liquibase.xml",
                new ClassLoaderResourceAccessor(),
                database
        );
        liquibase.update(new Contexts());
        return liquibase;
    }
}

