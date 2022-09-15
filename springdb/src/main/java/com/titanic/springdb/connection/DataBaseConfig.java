package com.titanic.springdb.connection;

public abstract class DataBaseConfig {
    public static class H2 {
        public static final String URL = "jdbc:h2:tcp://localhost/~/test";
        public static final String USER_NAME = "sa";
        public static final String PASSWORD = "";
    }

    public static class Mysql {
        public static final String URL = "jdbc:mysql://localhost:3306/jdbctest";
        public static final String USER_NAME = "root";
        public static final String PASSWORD = "root";
    }
}
