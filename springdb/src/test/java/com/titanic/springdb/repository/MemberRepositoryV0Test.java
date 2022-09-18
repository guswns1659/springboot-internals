package com.titanic.springdb.repository;

import com.titanic.springdb.connection.DBConnectionUtils;
import com.titanic.springdb.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV0Test {
    private static final String MEMBERID = "MEMBER1";
    private static final int money = 10000;
    private static final int newMoney = 20000;

    private MemberRepositoryV0 memberRepository = new MemberRepositoryV0();

    @Test
    void connection() {
        Connection con = DBConnectionUtils.getH2Connection();
        assertThat(con).isNotNull();
    }

    @Test
    void crud() throws SQLException {
        // create
        Member member = new Member(MEMBERID, money);
        memberRepository.save(member);

        // read
        Member findMember = memberRepository.findById(MEMBERID);
        log.info("findMember = {}", findMember);

        assertThat(findMember).isEqualTo(member);

        // update
        memberRepository.update(findMember.getMemberId(), newMoney);
        Member updatedMember = memberRepository.findById(findMember.getMemberId());
        log.info("updatedMember = {}", updatedMember);

        assertThat(updatedMember.getMoney()).isEqualTo(newMoney);

        // delete
        memberRepository.delete(updatedMember.getMemberId());
        assertThatThrownBy(() -> memberRepository.findById(updatedMember.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
