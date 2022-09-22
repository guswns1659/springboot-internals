package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.sql.SQLException;

@Slf4j
public class MemberServiceV3_1 {

    private final PlatformTransactionManager transactionManager;
    private final MemberRepositoryV3 memberRepositoryV3;

    public MemberServiceV3_1(PlatformTransactionManager transactionManager, MemberRepositoryV3 memberRepositoryV3) {
        this.transactionManager = transactionManager;
        this.memberRepositoryV3 = memberRepositoryV3;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {

        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            bizLogic(fromId, toId, amount);

            log.info("이체 성공");
            transactionManager.commit(transaction);
        } catch (Exception e) {
            transactionManager.rollback(transaction);
            log.error("이체 중 에러로 롤백");

            throw new IllegalStateException(e);
        }
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
