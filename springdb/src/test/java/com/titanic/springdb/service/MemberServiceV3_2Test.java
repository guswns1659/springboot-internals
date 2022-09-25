package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepositoryV3;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;

import static com.titanic.springdb.connection.DataBaseConfig.H2;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@Slf4j
class MemberServiceV3_2Test {

    private MemberServiceV3_2 memberServiceV3_2;
    private MemberRepositoryV3 memberRepositoryV3;

    private static final String MEMBER1 = "member1";
    private static final String MEMBER2 = "member2";
    private static final String EX_MEMBER = "exp";

    private Member member1;
    private Member member2;
    private Member exMember;

    @BeforeEach
    void setUp() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource(H2.URL, H2.USER_NAME, H2.PASSWORD);
        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        this.memberRepositoryV3 = new MemberRepositoryV3(dataSource);
        this.memberServiceV3_2 = new MemberServiceV3_2(transactionManager, memberRepositoryV3);

        member1 = new Member(MEMBER1, 10000);
        member2 = new Member(MEMBER2, 10000);
        exMember = new Member(EX_MEMBER, 10000);

        memberRepositoryV3.save(member1);
        memberRepositoryV3.save(member2);
        memberRepositoryV3.save(exMember);
        log.info("테스트를 위한 setUp 끝");

    }

    @AfterEach
    void tearDown() throws SQLException {
        log.info("tearDown start");
        memberRepositoryV3.delete(MEMBER1);
        memberRepositoryV3.delete(MEMBER2);
        memberRepositoryV3.delete(EX_MEMBER);
    }

    @Test
    void 이체중_실패_트랜잭션롤백() throws SQLException {
        // when
        Throwable throwable = catchThrowable(() -> memberServiceV3_2.accountTransfer(MEMBER1, EX_MEMBER, 2000));

        // then
        assertThat(throwable).isInstanceOf(IllegalStateException.class);

        Member findMember1 = memberRepositoryV3.findById(MEMBER1);
        Member exMember = memberRepositoryV3.findById(EX_MEMBER);
        assertThat(findMember1.getMoney()).isEqualTo(10000);
        assertThat(exMember.getMoney()).isEqualTo(10000);
    }
}
