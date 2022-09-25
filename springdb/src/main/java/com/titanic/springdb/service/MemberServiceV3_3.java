package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV3;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

public class MemberServiceV3_3 {
    private final MemberRepositoryV3 memberRepositoryV3;

    public MemberServiceV3_3(MemberRepositoryV3 memberRepositoryV3) {
        this.memberRepositoryV3 = memberRepositoryV3;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        bizLogic(fromId, toId, amount);
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
