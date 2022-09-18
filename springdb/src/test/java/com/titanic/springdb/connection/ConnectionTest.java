package com.titanic.springdb.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.H2;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataSource
 */
@Slf4j
class ConnectionTest {

    @Test
    void getConnection_with_driverManager() throws SQLException {
        // when
        Connection con1 = DriverManager.getConnection(H2.URL, H2.USER_NAME, H2.PASSWORD);
        Connection con2 = DriverManager.getConnection(H2.URL, H2.USER_NAME, H2.PASSWORD);

        // then
        log.info("getConnection = {}, con class = {}", con1, con1.getClass());
        log.info("getConnection = {}, con class = {}", con2, con2.getClass());
        assertThat(con1).isNotNull();
        assertThat(con2).isNotNull();
    }

    @Test
    void getConnection_with_dataSource() throws SQLException, InterruptedException {
        // when
        DriverManagerDataSource datasource = new DriverManagerDataSource(H2.URL, H2.USER_NAME, H2.PASSWORD);
        Connection con1 = datasource.getConnection();
        Connection con2 = datasource.getConnection();

        // then
        log.info("getConnection = {}, con class = {}", con1, con1.getClass());
        log.info("getConnection = {}, con class = {}", con2, con2.getClass());
        assertThat(con1).isNotNull();
        assertThat(con2).isNotNull();
        Thread.sleep(2000);
    }

    @Test
    void getConnection_with_connectionPool() throws SQLException, InterruptedException {
        // given
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(H2.URL);
        hikariConfig.setUsername(H2.USER_NAME);
        hikariConfig.setPassword(H2.PASSWORD);
        hikariConfig.setMaximumPoolSize(10);
        hikariConfig.setPoolName("Jack-pool");

        // when
        HikariDataSource datasource = new HikariDataSource(hikariConfig);
        Connection con1 = datasource.getConnection();
        Connection con2 = datasource.getConnection();

        // then
        log.info("getConnection = {}, con class = {}", con1, con1.getClass());
        log.info("getConnection = {}, con class = {}", con2, con2.getClass());
        assertThat(con1).isNotNull();
        Thread.sleep(2000);
    }
}
