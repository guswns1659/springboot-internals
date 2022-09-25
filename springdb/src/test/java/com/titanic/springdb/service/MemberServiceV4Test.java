package com.titanic.springdb.service;

import com.titanic.springdb.model.Member;
import com.titanic.springdb.repository.MemberRepository;
import com.titanic.springdb.repository.MemberRepositoryV5;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@SpringBootTest
@Slf4j
class MemberServiceV4Test {
    @Autowired
    private MemberServiceV4 memberServiceV4;
    @Autowired
    private MemberRepository memberRepository;

    private static final String MEMBER1 = "member1";
    private static final String MEMBER2 = "member2";
    private static final String EX_MEMBER = "exp";

    private Member member1;
    private Member member2;
    private Member exMember;

    @BeforeEach
    void setUp() {
        member1 = new Member(MEMBER1, 10000);
        member2 = new Member(MEMBER2, 10000);
        exMember = new Member(EX_MEMBER, 10000);

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(exMember);
    }

    @AfterEach
    void tearDown() {
        log.info("tearDown start");
        memberRepository.delete(MEMBER1);
        memberRepository.delete(MEMBER2);
        memberRepository.delete(EX_MEMBER);
    }

    @Test
    void 이체중_실패_트랜잭션롤백() {
        // when
        Throwable throwable = catchThrowable(() -> memberServiceV4.accountTransfer(MEMBER1, EX_MEMBER, 2000));

        // then
        assertThat(throwable).isInstanceOf(IllegalStateException.class);

        Member findMember1 = memberRepository.findById(MEMBER1);
        Member exMember = memberRepository.findById(EX_MEMBER);
        assertThat(findMember1.getMoney()).isEqualTo(10000);
        assertThat(exMember.getMoney()).isEqualTo(10000);
    }

    @Test
    void aop_test() {
        log.info("memberServiceV4_1 class = {}", memberServiceV4.getClass());
        log.info("memberRepository class = {}", memberRepository.getClass());
        assertThat(AopUtils.isAopProxy(memberServiceV4)).isTrue();
        assertThat(AopUtils.isAopProxy(memberRepository)).isFalse();
    }

    @TestConfiguration
    static class config {
        @Autowired
        private DataSource dataSource;

        @Bean
        public MemberRepository memberRepository() {
//            return new MemberRepositoryV4_1(dataSource); // 테스트가 중복돼서 DI만 적용
//            return new MemberRepositoryV4_2(dataSource);
            return new MemberRepositoryV5(dataSource);
        }

        @Bean
        public MemberServiceV4 memberServiceV4_1() {
            return new MemberServiceV4(memberRepository());
        }
    }
}
