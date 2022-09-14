package com.titanic.springdb;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV0;
import org.junit.jupiter.api.Test;

class MemberRepositoryV0Test {
    private MemberRepositoryV0 memberRepository = new MemberRepositoryV0();

    @Test
    void save() {
        // when
        memberRepository.save(new Member("MEMBER1", 10000));
    }
}
