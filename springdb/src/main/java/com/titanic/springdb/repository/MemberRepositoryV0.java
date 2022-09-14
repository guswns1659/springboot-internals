package com.titanic.springdb.repository;

import com.titanic.springdb.connection.DBConnectionUtils;
import com.titanic.springdb.model.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class MemberRepositoryV0 {

    public void save(Member member) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtils.getConnection();

            String sql = "insert into member (member_id, money) values (?, ?)";
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db save error", e);
        } finally {
            close(con, pstmt, null);
        }
    }

    private static void close(Connection con, PreparedStatement pstmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (Exception e) {
                log.error("rs close error", e);
            }
        }
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (Exception e) {
                log.error("pstmt connection close fail", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                log.error("connection close fail", e);
            }
        }
    }
}
