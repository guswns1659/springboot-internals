package com.titanic.springdb.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.SQLErrorCodeSQLExceptionTranslator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.H2;
import static org.assertj.core.api.Assertions.assertThat;

public class SpringExceptionTranslatorTest {

    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = new DriverManagerDataSource(H2.URL, H2.USER_NAME, H2.PASSWORD);
    }

    @Test
    void exceptionTranslator() {
        String sql = "select bad grammar";

        Connection con = null;
        PreparedStatement stmt = null;

        try {
            con = dataSource.getConnection();
            stmt= con.prepareStatement(sql);
            stmt.executeQuery();
        } catch (SQLException e) {
            assertThat(e.getErrorCode()).isEqualTo(42122);

            SQLErrorCodeSQLExceptionTranslator translator = new SQLErrorCodeSQLExceptionTranslator(dataSource);
            DataAccessException translatedExp = translator.translate("select", sql, e);

            assertThat(translatedExp).isInstanceOf(BadSqlGrammarException.class);
        } finally {
            JdbcUtils.closeStatement(stmt);
            JdbcUtils.closeConnection(con);
        }
    }
}
