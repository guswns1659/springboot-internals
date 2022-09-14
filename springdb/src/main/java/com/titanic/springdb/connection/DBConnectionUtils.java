package com.titanic.springdb.connection;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.*;

@Slf4j
public class DBConnectionUtils {

    public static Connection getConnection() {
        try {
            Connection con = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            log.info("get connection = {}, class = {}", con, con.getClass());
            return con;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
