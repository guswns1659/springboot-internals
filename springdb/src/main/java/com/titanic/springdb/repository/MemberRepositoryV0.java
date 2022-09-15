package com.titanic.springdb.repository;

import com.titanic.springdb.connection.DBConnectionUtils;
import com.titanic.springdb.model.Member;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {

    public void save(Member member) {

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtils.getMysqlConnection();

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

    public Member findById(String id) throws SQLException {

        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = DBConnectionUtils.getMysqlConnection();

            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, id);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                String memberId = rs.getString("member_id");
                int money = rs.getInt("money");
                return new Member(memberId, money);
            } else {
                throw new NoSuchElementException("member not found, memberId = {}" + id);
            }

        } catch (SQLException e) {
            log.error("findById error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        try {
            con = DBConnectionUtils.getMysqlConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
        } catch (SQLException e) {
            log.error("update error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }

    }

    public int delete(String memberId) throws SQLException {
        String sql = "delete from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = DBConnectionUtils.getMysqlConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            int resultSize = pstmt.executeUpdate();
            log.info("resultSize = {}", resultSize);
            return resultSize;
        } catch (SQLException e) {
            log.error("delete error", e);
            throw e;
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
