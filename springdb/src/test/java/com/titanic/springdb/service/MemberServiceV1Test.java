package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV1;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.H2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceV1Test {

    private static final String MEMBER1 = "member1";
    private static final String MEMBER2 = "member2";
    private static final String EX_MEMBER = "exp";

    private MemberServiceV1 memberServiceV1;
    private MemberRepositoryV1 memberRepositoryV1;

    private Member member1;
    private Member member2;
    private Member exMember;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource datasource = new DriverManagerDataSource(H2.URL, H2.USER_NAME, H2.PASSWORD);
        memberRepositoryV1 = new MemberRepositoryV1(datasource);
        memberServiceV1 = new MemberServiceV1(memberRepositoryV1);
        
        member1 = new Member(MEMBER1, 10000);
        member2 = new Member(MEMBER2, 10000);
        exMember = new Member(EX_MEMBER, 10000);

        memberRepositoryV1.save(member1);
        memberRepositoryV1.save(member2);
        memberRepositoryV1.save(exMember);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepositoryV1.delete(MEMBER1);
        memberRepositoryV1.delete(MEMBER2);
        memberRepositoryV1.delete(EX_MEMBER);
    }

    @Test
    void 정상이체() throws SQLException {
        // when
        memberServiceV1.accountTransfer(member1.getMemberId(), member2.getMemberId(), 2000);

        // then
        Member findMember1 = memberRepositoryV1.findById(member1.getMemberId());
        Member findMember2 = memberRepositoryV1.findById(member2.getMemberId());
        assertThat(findMember1.getMoney()).isEqualTo(8000);
        assertThat(findMember2.getMoney()).isEqualTo(12000);
    }

    @Test
    void 이체중_예외발생() throws SQLException {
        // when
        assertThatThrownBy(() -> memberServiceV1.accountTransfer(member1.getMemberId(), exMember.getMemberId(), 2000))
                .isInstanceOf(IllegalStateException.class);

        // then
        Member findMember1 = memberRepositoryV1.findById(MEMBER1);
        Member findExMember = memberRepositoryV1.findById(EX_MEMBER);
        assertThat(findMember1.getMoney()).isEqualTo(8000);
        assertThat(findExMember.getMoney()).isEqualTo(10000);
    }
}
