package com.titanic.springtx.propagation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * NOTE: 테스트가 전부 통과하지 못한다. @Transaction의 전파 옵션을 테스트 중이라 어노테이션의 전파 옵션에 따라 결과가 달라짐.
 */
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private LogRepository logRepository;

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional:ON Exception
     */
    @Test
    void recoverException_fail() {
        // given
        String username = "로그예외_recoverException_fail";

        // when
        assertThatThrownBy(() -> memberService.joinV2(username))
                .isInstanceOf(UnexpectedRollbackException.class);

        // then : member, log all rollback
        assertThat(memberRepository.findAny(username)).isEmpty();
        assertThat(logRepository.findByMessage(username)).isEmpty();
    }

    /**
     * MemberService    @Transactional:ON
     * MemberRepository @Transactional:ON
     * LogRepository    @Transactional(REQUIRES_NEW) Exception
     */
    @Test
    void recoverException_success() {
        // given
        String username = "로그예외_recoverException_success";

        // when
        memberService.joinV2(username);

        // then : member save, log rollback
        assertThat(memberRepository.findAny(username)).isPresent();
        assertThat(logRepository.findByMessage(username)).isEmpty();
    }
}
