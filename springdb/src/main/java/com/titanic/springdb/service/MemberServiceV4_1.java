package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

public class MemberServiceV4_1 {
    private MemberRepository memberRepository;

    public MemberServiceV4_1(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Transactional
    public void accountTransfer(String fromId, String toId, int amount) {
        bizLogic(fromId, toId, amount);
    }

    private void bizLogic(String fromId, String toId, int amount) {
        Member from = memberRepository.findById(fromId);
        Member to = memberRepository.findById(toId);

        memberRepository.update(from.getMemberId(), from.getMoney() - amount);
        validate(to);
        memberRepository.update(to.getMemberId(), to.getMoney() + amount);
    }

    private static void validate(Member to) {
        if (to.getMemberId().equals("exp")) {
            throw new IllegalStateException("이체 중 예외 발생");
        }
    }
}
