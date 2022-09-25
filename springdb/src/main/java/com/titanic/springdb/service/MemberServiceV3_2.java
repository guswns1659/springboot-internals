package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;

@Slf4j
public class MemberServiceV3_2 {
    private TransactionTemplate transactionTemplate;
    private MemberRepositoryV3 memberRepositoryV3;

    public MemberServiceV3_2(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepositoryV3) {
        this.transactionTemplate = new TransactionTemplate(transactionManager);
        this.memberRepositoryV3 = memberRepositoryV3;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {

        transactionTemplate.executeWithoutResult(status -> {
            try {
                bizLogic(fromId, toId, amount);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void bizLogic(String fromId, String toId, int amount) throws SQLException {
        Member from = memberRepositoryV3.findById(fromId);
        Member to = memberRepositoryV3.findById(toId);

        memberRepositoryV3.update(from.getMemberId(), from.getMoney() - amount);
        validate(to);
        memberRepositoryV3.update(to.getMemberId(), to.getMoney() + amount);
    }

    private static void validate(Member to) {
        if (to.getMemberId().equals("exp")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
