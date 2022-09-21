package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV2;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.H2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MemberServiceV2Test {

    private static final String MEMBER1 = "member1";
    private static final String MEMBER2 = "member2";
    private static final String EX_MEMBER = "exp";

    private MemberServiceV2 memberServiceV2;
    private MemberRepositoryV2 memberRepositoryV2;

    private Member member1;
    private Member member2;
    private Member exMember;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(H2.URL, H2.USER_NAME, H2.PASSWORD);
        memberRepositoryV2 = new MemberRepositoryV2(dataSource);
        memberServiceV2 = new MemberServiceV2(dataSource, memberRepositoryV2);

        member1 = new Member(MEMBER1, 10000);
        member2 = new Member(MEMBER2, 10000);
        exMember = new Member(EX_MEMBER, 10000);

        memberRepositoryV2.save(member1);
        memberRepositoryV2.save(member2);
        memberRepositoryV2.save(exMember);
    }

    @AfterEach
    void tearDown() throws SQLException {
        memberRepositoryV2.delete(MEMBER1);
        memberRepositoryV2.delete(MEMBER2);
        memberRepositoryV2.delete(EX_MEMBER);
    }

    @Test
    void 이체중_실패_트랜잭션롤백성공() throws SQLException {
        // when
        assertThatThrownBy(() -> memberServiceV2.accountTransfer(MEMBER1, EX_MEMBER, 2000))
                .isInstanceOf(IllegalStateException.class);

        // then
        Member findMember1 = memberRepositoryV2.findById(MEMBER1);
        Member findExMember = memberRepositoryV2.findById(EX_MEMBER);
        assertThat(findMember1.getMoney()).isEqualTo(10000);
        assertThat(findExMember.getMoney()).isEqualTo(10000);
    }
}
