package com.titanic.springdb.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.*;

@Slf4j
public class DBConnectionUtils {

    public static Connection getH2Connection() {
        try {
            Connection con = DriverManager.getConnection(H2.URL, H2.USER_NAME, H2.PASSWORD);
            log.info("get connection = {}, class = {}", con, con.getClass());
            return con;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    public static Connection getMysqlConnection() {
        try {
            Connection con = DriverManager.getConnection(Mysql.URL, Mysql.USER_NAME, Mysql.PASSWORD);
            log.info("get connection = {}, class = {}", con, con.getClass());
            return con;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
