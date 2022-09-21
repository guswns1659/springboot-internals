package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV2;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Slf4j
public class MemberServiceV2 {

    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepositoryV2;

    public MemberServiceV2(DataSource dataSource, MemberRepositoryV2 memberRepositoryV2) {
        this.dataSource = dataSource;
        this.memberRepositoryV2 = memberRepositoryV2;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {

        Connection con = dataSource.getConnection();

        try {
            con.setAutoCommit(false);
            bizLogic(con, fromId, toId, amount);

            log.info("이체 성공");
            con.commit();
        } catch (Exception e) {
            con.rollback();
            log.error("이체 중 에러로 롤백");

            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    private static void release(Connection con) throws SQLException {
        if (con != null) {
            try {
                con.setAutoCommit(true);
                con.close();
            } catch (Exception e) {
                log.error("error", e);
            }
        }
    }

    private void bizLogic(Connection con, String fromId, String toId, int amount) throws SQLException {
        Member from = memberRepositoryV2.findById(con, fromId);
        Member to = memberRepositoryV2.findById(con, toId);

        memberRepositoryV2.update(con, from.getMemberId(), from.getMoney() - amount);
        validate(to);
        memberRepositoryV2.update(con, to.getMemberId(), to.getMoney() + amount);
    }

    private static void validate(Member to) {
        if (to.getMemberId().equals("exp")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
