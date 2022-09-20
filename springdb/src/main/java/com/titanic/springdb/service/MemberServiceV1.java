package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV1;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class MemberServiceV1 {

    private final MemberRepositoryV1 memberRepositoryV1;

    public MemberServiceV1(MemberRepositoryV1 memberRepositoryV1) {
        this.memberRepositoryV1 = memberRepositoryV1;
    }

    public void accountTransfer(String fromId, String toId, int amount) throws SQLException {
        Member from = memberRepositoryV1.findById(fromId);
        Member to = memberRepositoryV1.findById(toId);

        memberRepositoryV1.update(from.getMemberId(), from.getMoney() - amount);
        validate(to);
        memberRepositoryV1.update(to.getMemberId(), to.getMoney() + amount);

    }

    private static void validate(Member to) {
        if (to.getMemberId().equals("exp")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
